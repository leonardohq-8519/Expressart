package org.project.expressart.Chat.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.dto.ChatRequestDTO;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private OrdenRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ChatService chatService;

    private Chat chat;
    private ChatResponseDTO responseDTO;
    private ChatRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(chatService, "modelMapper", modelMapper);

        chat = new Chat();
        chat.setId(1L);

        responseDTO = new ChatResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setOrdenId(10L);

        requestDTO = new ChatRequestDTO();
        requestDTO.setOrdenId(10L);
    }

    @Test
    void shouldReturnAllChatsWhenFindAll() {
        when(chatRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<ChatResponseDTO> result = chatService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(chatRepository).findAllBy(any(Pageable.class));
    }

    @Test
    void shouldReturnChatWhenFindByIdAndExists() {
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));
        when(modelMapper.map(chat, ChatResponseDTO.class)).thenReturn(responseDTO);

        ChatResponseDTO result = chatService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenFindByIdAndNotExists() {
        when(chatRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnChatWhenFindByOrderIdAndExists() {
        when(chatRepository.findByOrderId(10L)).thenReturn(Optional.of(chat));
        when(modelMapper.map(chat, ChatResponseDTO.class)).thenReturn(responseDTO);

        ChatResponseDTO result = chatService.findByOrderId(10L);

        assertThat(result).isNotNull();
        assertThat(result.getOrdenId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenFindByOrderIdAndNotExists() {
        when(chatRepository.findByOrderId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatService.findByOrderId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldDeleteChatWhenExistsById() {
        when(chatRepository.existsById(1L)).thenReturn(true);
        doNothing().when(chatRepository).deleteById(1L);

        chatService.delete(1L);

        verify(chatRepository).deleteById(1L);
    }
}
