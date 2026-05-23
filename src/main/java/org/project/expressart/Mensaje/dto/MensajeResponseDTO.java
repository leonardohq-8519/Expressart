package org.project.expressart.Mensaje.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class MensajeResponseDTO {
    private Long id;
    private Long chatId;
    private Long remitenteId;
    private String nombreRemitente;
    private String texto;
    private ZonedDateTime fechaEnvio;
}