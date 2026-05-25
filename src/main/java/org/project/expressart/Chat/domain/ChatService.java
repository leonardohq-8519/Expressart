package org.project.expressart.Chat.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final OrdenRepository orderRepository;
    public List<ChatResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return chatRepository.findAllBy(pageable);
    }
    public ChatResponseDTO  findById (Long id)throws ResourceNotFoundException {
        Chat chat = chatRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Chat not found"));
        return modelMapper.map(chat, ChatResponseDTO.class);
    }
    public ChatResponseDTO findByOrderId (Long orderId)throws ResourceNotFoundException {
        Chat chat = chatRepository.findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Chat not found"));
        return modelMapper.map(chat, ChatResponseDTO.class);
    }
    public ChatResponseDTO create(ChatRequestDTO request){
        Chat chat = new Chat();
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        chat.setOrden(order);
        chatRepository.save(chat);
        return modelMapper.map(chat, ChatResponseDTO.class);
    }
    public ChatResponseDTO  update (Long id, ChatRequestDTO request)throws ResourceNotFoundException {
        Chat updatedChat = chatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chat not found"));
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        updatedChat.setOrden(order);
        chatRepository.save(updatedChat);
        return modelMapper.map(updatedChat, ChatResponseDTO.class);
    }
    public void delete (Long id){
        if (chatRepository.existsById(id))
            chatRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Chat with ID " + id + " doesn't exist");
    }

}