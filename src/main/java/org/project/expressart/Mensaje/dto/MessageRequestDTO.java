package org.project.expressart.Mensaje.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDTO {
    private Long chatId;
    private Long remitenteId;
    private String texto;
}