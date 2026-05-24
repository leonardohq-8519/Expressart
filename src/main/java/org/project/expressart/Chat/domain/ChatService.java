package org.project.expressart.Chat.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService{
    private final ChatRepository chatRepository;
    public List<ChatResponseDTO> findAll(){
    }
    public ChatResponseDTO  findById (Long id){
    }
    public ChatResponseDTO findByOrderId (Long orderId){
    }
    public ChatResponseDTO create(ChatRequestDTO request){
    }
    public ChatResponseDTO  update (Long id, ChatRequestDTO request){
    }
    public void delete (Long id){
    }

}