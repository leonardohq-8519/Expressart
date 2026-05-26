package org.project.expressart.Chat.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Chat.domain.ChatService;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ChatController.class)
@WithMockUser
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ChatService chatService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ChatResponseDTO responseDTO;
    private ChatRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ChatResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setOrdenId(10L);

        requestDTO = new ChatRequestDTO();
        requestDTO.setOrdenId(10L);
    }

    @Test
    void shouldReturnAllChatsWhenGetAll() throws Exception {
        when(chatService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/chats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ordenId").value(10));
    }

    @Test
    void shouldReturnChatWhenGetByIdAndExists() throws Exception {
        when(chatService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/chats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndChatNotFound() throws Exception {
        when(chatService.findById(99L)).thenThrow(new ResourceNotFoundException("Chat not found"));

        mockMvc.perform(get("/chats/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnChatWhenGetByOrderIdAndExists() throws Exception {
        when(chatService.findByOrderId(10L)).thenReturn(responseDTO);

        mockMvc.perform(get("/chats/order/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ordenId").value(10));
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        when(chatService.create(any(ChatRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(chatService).delete(1L);

        mockMvc.perform(delete("/chats/1"))
                .andExpect(status().isNoContent());
    }
}
