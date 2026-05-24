package org.project.expressart.Mensaje.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Mensaje.infrastructure.MensajeRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final MensajeRepository messageRepository;

    public List<MessageResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Mensaje> mensajes = messageRepository.findAll(pageable).getContent();
        return convertToDtoList(mensajes);
    }

    public MessageResponseDTO findById(Long id) {
        Mensaje message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Message not found"));
        return modelMapper.map(message, MessageResponseDTO.class);
    }

    public List<MessageResponseDTO> findByChatId(Long chatId) {
        List<Mensaje> mensajes = messageRepository.findByChatId(chatId);
        return convertToDtoList(mensajes);
    }

    public MessageResponseDTO create(MessageRequestDTO request) {
        Mensaje message = modelMapper.map(request, Mensaje.class);
        Mensaje savedMessage = messageRepository.save(message);
        return modelMapper.map(savedMessage, MessageResponseDTO.class);
    }

    public MessageResponseDTO markAsRead(Long id) {
        Mensaje existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Message not found"));

        existingMessage.setLeido(true);

        Mensaje updatedMessage = messageRepository.save(existingMessage);
        return modelMapper.map(updatedMessage, MessageResponseDTO.class);
    }

    public void delete(Long id) {
        if (messageRepository.existsById(id))
            messageRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Message with ID " + id + " doesn't exist");
    }

    private List<MessageResponseDTO> convertToDtoList(List<Mensaje> mensajes) {
        return mensajes.stream()
                .map(message -> modelMapper.map(message, MessageResponseDTO.class))
                .collect(Collectors.toList());
    }
}