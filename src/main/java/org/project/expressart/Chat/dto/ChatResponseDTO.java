package org.project.expressart.Chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Mensaje.dto.MensajeResponseDTO;

import java.util.List;

@Getter
@Setter
public class ChatResponseDTO {
    private Long id;
    private Long ordenId;
    private List<MensajeResponseDTO> mensajes;
}