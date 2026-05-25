package org.project.expressart.Mensaje.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Mensaje.domain.MessageService;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MensajeControllerTest {

    private MockMvc mockMvc;
    private MessageService messageService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        MensajeController mensajeController = new MensajeController(messageService);
        mockMvc = MockMvcBuilders.standaloneSetup(mensajeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_DebeRetornarEstatus200YListaDeMensajes() throws Exception {
        // Arrange
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageService.findAll()).thenReturn(List.of(responseDTO));

        // Act & Assert
        mockMvc.perform(get("/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(messageService, times(1)).findAll();
    }

    @Test
    void getById_DebeRetornarEstatus200YMensaje() throws Exception {
        // Arrange
        Long messageId = 1L;
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageService.findById(messageId)).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(get("/messages/{id}", messageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(messageService, times(1)).findById(messageId);
    }

    @Test
    void getByChat_DebeRetornarEstatus200YListaDeMensajes() throws Exception {
        // Arrange
        Long chatId = 10L;
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageService.findByChatId(chatId)).thenReturn(List.of(responseDTO));

        // Act & Assert
        mockMvc.perform(get("/messages/chat/{chatId}", chatId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(messageService, times(1)).findByChatId(chatId);
    }

    @Test
    void create_DebeRetornarEstatus201YMensajeCreado() throws Exception {
        // Arrange
        MessageRequestDTO requestDTO = new MessageRequestDTO();
        requestDTO.setChatId(10L);
        requestDTO.setRemitenteId(2L);
        requestDTO.setTexto("Hola, este es un mensaje de prueba.");

        MessageResponseDTO responseDTO = new MessageResponseDTO();

        when(messageService.create(any(MessageRequestDTO.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        verify(messageService, times(1)).create(any(MessageRequestDTO.class));
    }

    @Test
    void markAsRead_DebeRetornarEstatus200YMensajeModificado() throws Exception {
        // Arrange
        Long messageId = 1L;
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageService.markAsRead(messageId)).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(patch("/messages/{id}/read", messageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(messageService, times(1)).markAsRead(messageId);
    }

    @Test
    void delete_DebeRetornarEstatus204NoContent() throws Exception {
        // Arrange
        Long messageId = 1L;
        doNothing().when(messageService).delete(messageId);

        // Act & Assert
        mockMvc.perform(delete("/messages/{id}", messageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(messageService, times(1)).delete(messageId);
    }
}