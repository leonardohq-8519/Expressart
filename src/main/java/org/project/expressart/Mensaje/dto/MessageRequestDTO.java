package org.project.expressart.Mensaje.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Mensaje.domain.TipoArchivo;

@Getter
@Setter
public class MessageRequestDTO {
    @NotNull(message = "El chatId es obligatorio")
    private Long chatId;
    private Long remitenteId;
    private String contenido;
    private String archivoUrl;
    private TipoArchivo tipoArchivo;
    private String texto;
}