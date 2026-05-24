package org.project.expressart.Mensaje.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Mensaje.infrastructure.MensajeRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final MensajeRepository messageRepository;
    public List<MessageResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return messageRepository.findAllBy(pageable);
    }
    public MessageResponseDTO  findById (Long id){
        Mensaje message = messageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Message not found"));
        return modelMapper.map(message, MessageResponseDTO.class);
    }
    public MessageResponseDTO findByChatId (Long chatId){
        Mensaje message = messageRepository.findByChatId(chatId).orElseThrow(()-> new ResourceNotFoundEXception("Message not found"));
        return modelMapper.map(message, MessageResponseDTO.class);
    }
    public MessageResponseDTO create(MessageRequestDTO request){
    }
    public MessageResponseDTO  markAsRead (Long id){
    }
    public void delete (Long id){
        if (messageRepository.existsById(id))
            messageRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Message with ID " + id + " doesn't exist");
    }

}