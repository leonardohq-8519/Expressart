package org.project.expressart.Mensaje.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Mensaje.infrastructure.MensajeRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MensajeRepository messageRepository;
    private ChatRepository chatRepository;
    private UsuarioRepository userRepository;
    private ModelMapper modelMapper;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MensajeRepository.class);
        chatRepository = mock(ChatRepository.class);
        userRepository = mock(UsuarioRepository.class);
        modelMapper = mock(ModelMapper.class);

        messageService = new MessageService(messageRepository, chatRepository, userRepository);

        try {
            java.lang.reflect.Field field = MessageService.class.getDeclaredField("modelMapper");
            field.setAccessible(true);
            field.set(messageService, modelMapper);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAll_DebeRetornarListaDeMensajes() {
        Pageable pageable = PageRequest.of(0, 10);

        // 1. Creamos una entidad Mensaje real (vacía o simulada) para el mock del repositorio
        Mensaje mensaje = new Mensaje();
        mensaje.setTexto("Mensaje de prueba");

        // 2. CORRECCIÓN CRÍTICA: El repositorio devuelve entidades 'Mensaje', NO DTOs
        when(messageRepository.findAllBy(pageable)).thenReturn(List.of(mensaje));

        // 3. Ejecutamos el método del servicio (este internamente mapeará a MessageResponseDTO)
        List<MessageResponseDTO> result = messageService.findAll();

        // 4. Verificaciones
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(messageRepository, times(1)).findAllBy(pageable);
    }

    @Test
    void findById_DebeRetornarMensajeCuandoExiste() throws ResourceNotFoundException {
        Long messageId = 1L;
        Mensaje mensaje = new Mensaje();
        MessageResponseDTO responseDTO = new MessageResponseDTO();

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(mensaje));
        when(modelMapper.map(mensaje, MessageResponseDTO.class)).thenReturn(responseDTO);

        MessageResponseDTO result = messageService.findById(messageId);

        assertNotNull(result);
        verify(messageRepository, times(1)).findById(messageId);
    }

    @Test
    void findById_DebeLanzarExcepcionCuandoNoExiste() {
        Long messageId = 1L;
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> messageService.findById(messageId));
        verify(messageRepository, times(1)).findById(messageId);
    }

    @Test
    void findByChatId_DebeRetornarListaCuandoExistenMensajes() throws ResourceNotFoundException {
        Long chatId = 1L;
        Mensaje mensaje = new Mensaje();
        MessageResponseDTO responseDTO = new MessageResponseDTO();

        when(messageRepository.findByChatId(chatId)).thenReturn(List.of(mensaje));
        when(modelMapper.map(any(), eq(MessageResponseDTO.class))).thenReturn(responseDTO);

        List<MessageResponseDTO> result = messageService.findByChatId(chatId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(messageRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void findByChatId_DebeLanzarExcepcionCuandoChatNoTieneMensajes() {
        Long chatId = 1L;
        when(messageRepository.findByChatId(chatId)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> messageService.findByChatId(chatId));
        verify(messageRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void create_DebeGuardarYRetornarMensaje() {
        MessageRequestDTO request = new MessageRequestDTO();
        request.setChatId(1L);
        request.setRemitenteId(1L);
        request.setTexto("Test");

        Chat chat = new Chat();
        Usuario usuario = new Usuario();
        Mensaje mensaje = new Mensaje();
        MessageResponseDTO responseDTO = new MessageResponseDTO();

        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(messageRepository.save(any(Mensaje.class))).thenReturn(mensaje);
        when(modelMapper.map(any(Mensaje.class), eq(MessageResponseDTO.class))).thenReturn(responseDTO);

        MessageResponseDTO result = messageService.create(request);

        assertNotNull(result);
        verify(messageRepository, times(1)).save(any(Mensaje.class));
    }

    @Test
    void create_DebeLanzarExcepcionCuandoChatNoExiste() {
        MessageRequestDTO request = new MessageRequestDTO();
        request.setChatId(1L);

        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> messageService.create(request));
    }

    @Test
    void markAsRead_DebeModificarEstadoLeido() throws ResourceNotFoundException {
        Long messageId = 1L;
        Mensaje mensaje = new Mensaje();
        mensaje.setLeido(false);
        MessageResponseDTO responseDTO = new MessageResponseDTO();

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(mensaje));
        when(messageRepository.save(mensaje)).thenReturn(mensaje);
        when(modelMapper.map(mensaje, MessageResponseDTO.class)).thenReturn(responseDTO);

        MessageResponseDTO result = messageService.markAsRead(messageId);

        assertNotNull(result);
        assertTrue(mensaje.getLeido());
        verify(messageRepository, times(1)).save(mensaje);
    }

    @Test
    void delete_DebeBorrarSiExiste() {
        Long messageId = 1L;
        when(messageRepository.existsById(messageId)).thenReturn(true);
        doNothing().when(messageRepository).deleteById(messageId);

        assertDoesNotThrow(() -> messageService.delete(messageId));
        verify(messageRepository, times(1)).deleteById(messageId);
    }

    @Test
    void delete_DebeLanzarExcepcionSiNoExiste() {
        Long messageId = 1L;
        when(messageRepository.existsById(messageId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> messageService.delete(messageId));
    }
}