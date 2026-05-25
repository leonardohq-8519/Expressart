package org.project.expressart.Chat.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Chat.domain.ChatService;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ChatController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = org.project.expressart.config.JwtAuthFilter.class
        )
)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ChatService chatService;

    private ChatResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ChatResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setOrdenId(1L);
    }

    @Test
    @DisplayName("Debería retornar 200 y lista de chats al obtener todos")
    void shouldReturn200WithChatListWhenGetAll() throws Exception {
        when(chatService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/chats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ordenId").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 con el chat cuando existe por ID")
    void shouldReturn200WhenChatFoundById() throws Exception {
        when(chatService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/chats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 con el chat cuando existe por orden ID")
    void shouldReturn200WhenChatFoundByOrderId() throws Exception {
        when(chatService.findByOrderId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/chats/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear un chat válido")
    void shouldReturn201WhenChatCreatedSuccessfully() throws Exception {
        ChatRequestDTO request = new ChatRequestDTO();
        request.setOrdenId(1L);

        when(chatService.create(any(ChatRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar un chat existente")
    void shouldReturn204WhenChatDeletedSuccessfully() throws Exception {
        doNothing().when(chatService).delete(1L);

        mockMvc.perform(delete("/chats/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay chats")
    void shouldReturnEmptyListWhenNoChats() throws Exception {
        when(chatService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/chats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}