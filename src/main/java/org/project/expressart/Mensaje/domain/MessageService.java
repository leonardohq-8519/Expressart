package org.project.expressart.Mensaje.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Mensaje.infrastructure.MensajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService{
    private final MensajeRepository messageRepository;
    public List<MessageResponseDTO> findAll(){
    }
    public MessageResponseDTO  findById (Long id){
    }
    public MessageResponseDTO findByChatId (Long chatId){
    }
    public MessageResponseDTO create(MessageRequestDTO request){
    }
    public MessageResponseDTO  markAsRead (Long id){
    }
    public void delete (Long id){
    }

}