package org.project.expressart.Chat.infrastructure;

import org.project.expressart.Chat.application.ChatService;
import org.project.expressart.Chat.application.dto.ChatRequestDTO;
import org.project.expressart.Chat.application.dto.ChatResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<ChatResponseDTO>> getAll() {
        return ResponseEntity.ok(chatService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ChatResponseDTO> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(chatService.findByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<ChatResponseDTO> create(@RequestBody ChatRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}