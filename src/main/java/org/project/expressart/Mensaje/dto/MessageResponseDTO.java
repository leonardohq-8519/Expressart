package org.project.expressart.Mensaje.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Mensaje.domain.TipoArchivo;

import java.time.ZonedDateTime;

@Getter
@Setter
public class MessageResponseDTO {
    private Long id;
    private Long chatId;
    private Long remitenteId;
    private String remitenteUsername;
    private String contenido;
    private String archivoUrl;
    private TipoArchivo tipoArchivo;
    private Boolean leido;
    private ZonedDateTime fechaEnvio;
}