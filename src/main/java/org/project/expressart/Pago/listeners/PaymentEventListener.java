package org.project.expressart.Pago.listeners;

import lombok.RequiredArgsConstructor;
import org.project.expressart.Pago.events.PagoExitosoEvent;
import org.project.expressart.NotificacionCorreo.domain.EmailNotificationService;
import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.NotificacionCorreo;
import org.project.expressart.NotificacionCorreo.domain.TipoCorreo;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.project.expressart.NotificacionCorreo.infrastructure.NotificacionCorreoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
@RequiredArgsConstructor
@Component
public class PaymentEventListener {
    private final EmailNotificationService emailNotificationService;
    private final NotificacionCorreoRepository emailNotificationRepository;
    @Autowired(required = false)
    private final JavaMailSender mailSender;

    @Async
    @EventListener
    public void handlePagoExitoso(PagoExitosoEvent event) {

        NotificacionCorreoCreateDTO correoDTO = new NotificacionCorreoCreateDTO();
        correoDTO.setUsuarioId(event.clienteId());
        correoDTO.setDestinatarioEmail(event.clienteEmail());
        correoDTO.setAsunto("¡Confirmación de pago para tu Orden #" + event.ordenId() + "!");

        String cuerpoMensaje = (
                "¡Hola!\n\nSe ha procesado tu pago por el monto de S/. "+event.monto().toString()+
                        " correspondientes a tu orden de comisión " + event.ordenId() +
                        "\nEl artista iniciará la fase de producción de tu obra.\n\nAtentamente,\nExpressart."
        );
        correoDTO.setMensaje(cuerpoMensaje);
        correoDTO.setTipo(TipoCorreo.PAGO_CONFIRMADO);

        try {
            NotificacionCorreoResponseDTO correoGuardado = emailNotificationService.create(correoDTO);

            NotificacionCorreo emailNotif = emailNotificationRepository.findById(correoGuardado.getId()).orElse(null);

            if (emailNotif == null) return;

            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(emailNotif.getDestinatarioEmail());
                message.setSubject(emailNotif.getAsunto());
                message.setText(emailNotif.getMensaje());
                message.setFrom("notifications.expressart@gmail.com");

                mailSender.send(message);
                emailNotif.setEstado(EstadoCorreo.ENVIADO);
                emailNotif.setFechaEnvio(ZonedDateTime.now());

            } catch (Exception smtpException) {
                emailNotif.setEstado(EstadoCorreo.FALLIDO);
                emailNotif.setIntentos(emailNotif.getIntentos() + 1);

                String errorTrace = smtpException.getMessage();
                if (errorTrace != null && errorTrace.length() > 255) {
                    errorTrace = errorTrace.substring(0, 255);
                }
                emailNotif.setError(errorTrace);
            } finally {
                emailNotificationRepository.save(emailNotif);
            }
        } catch (Exception dbException) {
            System.err.println("Fatal error generating email audit record: " + dbException.getMessage());
        }
    }
}