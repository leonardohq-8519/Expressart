package org.project.expressart.Notificacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Notificacion.domain.TipoNotificacion;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Long id;
    private TipoNotificacion tipo;
    private String titulo;
    private String mensaje;
    private Boolean leida;
    private String urlDestino;
    private ZonedDateTime fechaCreacion;
    private ZonedDateTime fechaLectura;
}