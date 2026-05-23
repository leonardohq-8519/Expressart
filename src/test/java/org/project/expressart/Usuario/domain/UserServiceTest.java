package org.project.expressart.Usuario.domain;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Usuario.dto.UserRequestDTO;
import org.project.expressart.Usuario.dto.UserResponseDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private Usuario usuario;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setEmail("test@email.com");
        usuario.setName("Test User");
        usuario.setPassword("password123");
        usuario.setIsActive(true);

        requestDTO = new UserRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setEmail("test@email.com");
        requestDTO.setName("Test User");
        requestDTO.setPassword("password123");

        responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUsername("testuser");
        responseDTO.setEmail("test@email.com");
        responseDTO.setName("Test User");
    }

    @Test
    void findAll_debeRetornarListaDeUsuarios() {
        Pageable pageable = PageRequest.of(0, 10);
        when(usuarioRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<UserResponseDTO> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(usuarioRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void create_debeCrearUsuario_cuandoDatosValidos() throws BadRequestException {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(modelMapper.map(any(Usuario.class), eq(UserResponseDTO.class))).thenReturn(responseDTO);

        UserResponseDTO result = userService.create(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void create_debeLanzarExcepcion_cuandoEmailYaExiste() {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(requestDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("El email ya está en uso");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void create_debeLanzarExcepcion_cuandoUsernameYaExiste() {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(requestDTO.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(requestDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("El username ya está en uso");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void findById_debeRetornarUsuario_cuandoExisteId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(any(Usuario.class), eq(UserResponseDTO.class))).thenReturn(responseDTO);

        UserResponseDTO result = userService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_debeLanzarExcepcion_cuandoNoExisteId() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void findByEmail_debeRetornarUsuario_cuandoExisteEmail() {
        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(Optional.of(usuario));
        when(modelMapper.map(any(Usuario.class), eq(UserResponseDTO.class))).thenReturn(responseDTO);

        UserResponseDTO result = userService.findByEmail("test@email.com");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void delete_debeEliminarUsuario_cuandoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_debeLanzarExcepcion_cuandoNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.delete(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("doesn't exist");
    }

    @Test
    void update_debeActualizarUsuario_cuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(modelMapper.map(any(Usuario.class), eq(UserResponseDTO.class))).thenReturn(responseDTO);

        UserResponseDTO result = userService.update(1L, requestDTO);

        assertThat(result).isNotNull();
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void update_debeLanzarExcepcion_cuandoNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(99L, requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void findSeguidoresByArtistaId_debeRetornarLista() {
        when(usuarioRepository.findSeguidoresByArtistaId(1L)).thenReturn(List.of(usuario));
        when(modelMapper.map(any(Usuario.class), eq(UserResponseDTO.class))).thenReturn(responseDTO);

        List<UserResponseDTO> result = userService.findSeguidoresByArtistaId(1L);

        assertThat(result).hasSize(1);
        verify(usuarioRepository, times(1)).findSeguidoresByArtistaId(1L);
    }
}