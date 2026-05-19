package org.project.expressart.Mensaje.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeRequestDTO {
    private Long chatId;
    private Long remitenteId;
    private String texto;
}