package org.project.expressart.Chat.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private OrdenRepository orderRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private ChatService chatService;

    private Chat chat;
    private ChatRequestDTO requestDTO;
    private ChatResponseDTO responseDTO;
    private Orden orden;

    @BeforeEach
    void setUp() {
        chatService = new ChatService(chatRepository, orderRepository);
        ReflectionTestUtils.setField(chatService, "modelMapper", modelMapper);

        orden = new Orden();
        orden.setId(1L);

        chat = new Chat();
        chat.setId(1L);
        chat.setOrden(orden);
        chat.setCanalRedis("chat:1");

        requestDTO = new ChatRequestDTO();
        requestDTO.setOrdenId(1L);

        responseDTO = new ChatResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setOrdenId(1L);
    }

    @Test
    @DisplayName("Debería retornar lista de chats al llamar findAll")
    void shouldReturnChatListWhenFindAll() {
        when(chatRepository.findAllBy(any())).thenReturn(List.of(responseDTO));

        List<ChatResponseDTO> result = chatService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chatRepository, times(1)).findAllBy(any());
    }

    @Test
    @DisplayName("Debería retornar chat cuando existe el ID")
    void shouldReturnChatWhenFindByIdExists() throws ResourceNotFoundException {
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        ChatResponseDTO result = chatService.findById(1L);

        assertNotNull(result);
        verify(chatRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el chat no existe por ID")
    void shouldThrowExceptionWhenChatNotFoundById() {
        when(chatRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chatService.findById(99L));
    }

    @Test
    @DisplayName("Debería retornar chat cuando existe por orden ID")
    void shouldReturnChatWhenFindByOrderIdExists() throws ResourceNotFoundException {
        when(chatRepository.findByOrderId(1L)).thenReturn(Optional.of(chat));

        ChatResponseDTO result = chatService.findByOrderId(1L);

        assertNotNull(result);
        verify(chatRepository, times(1)).findByOrderId(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no existe chat para el orden")
    void shouldThrowExceptionWhenChatNotFoundByOrderId() {
        when(chatRepository.findByOrderId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chatService.findByOrderId(99L));
    }

    @Test
    @DisplayName("Debería crear un chat cuando la orden existe")
    void shouldCreateChatWhenOrdenExists() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        ChatResponseDTO result = chatService.create(requestDTO);

        assertNotNull(result);
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al crear chat cuando la orden no existe")
    void shouldThrowExceptionWhenCreatingChatWithNonExistentOrden() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> chatService.create(requestDTO));
        verify(chatRepository, never()).save(any(Chat.class));
    }

    @Test
    @DisplayName("Debería eliminar chat cuando existe el ID")
    void shouldDeleteChatWhenExists() {
        when(chatRepository.existsById(1L)).thenReturn(true);
        doNothing().when(chatRepository).deleteById(1L);

        chatService.delete(1L);

        verify(chatRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción al eliminar chat cuando no existe")
    void shouldThrowExceptionWhenDeletingNonExistentChat() {
        when(chatRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> chatService.delete(99L));
        verify(chatRepository, never()).deleteById(anyLong());
    }
}