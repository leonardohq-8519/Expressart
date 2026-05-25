package org.project.expressart.Chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class ChatResponseDTO {
    private Long id;
    private Long ordenId;
    private String redisChannel;
    private ZonedDateTime fechaCreacion;
    private List<MessageResponseDTO> mensajes;
}