package org.project.expressart.Chat.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final ChatRepository chatRepository;

    public List<ChatResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Chat> chats = chatRepository.findAll(pageable).getContent();
        return convertToDtoList(chats);
    }

    public ChatResponseDTO findById(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Chat not found"));
        return modelMapper.map(chat, ChatResponseDTO.class);
    }

    public ChatResponseDTO findByOrderId(Long orderId) {
        Chat chat = chatRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundEXception("Chat not found"));
        return modelMapper.map(chat, ChatResponseDTO.class);
    }

    public ChatResponseDTO create(ChatRequestDTO request) {
        Chat chat = modelMapper.map(request, Chat.class);
        Chat savedChat = chatRepository.save(chat);
        return modelMapper.map(savedChat, ChatResponseDTO.class);
    }

    public ChatResponseDTO update(Long id, ChatRequestDTO request) {
        Chat existingChat = chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Chat not found"));

        modelMapper.map(request, existingChat);
        existingChat.setId(id);

        Chat updatedChat = chatRepository.save(existingChat);
        return modelMapper.map(updatedChat, ChatResponseDTO.class);
    }

    public void delete(Long id) {
        if (chatRepository.existsById(id))
            chatRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Chat with ID " + id + " doesn't exist");
    }

    private List<ChatResponseDTO> convertToDtoList(List<Chat> chats) {
        return chats.stream()
                .map(chat -> modelMapper.map(chat, ChatResponseDTO.class))
                .collect(Collectors.toList());
    }
}