package org.project.expressart.Usuario.domain;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Usuario.dto.UserRequestDTO;
import org.project.expressart.Usuario.dto.UserResponseDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityNotFoundException;
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

    private ModelMapper modelMapper;
    private UserService userService;

    private Usuario usuario;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userService = new UserService(usuarioRepository);

        try {
            java.lang.reflect.Field field = UserService.class.getDeclaredField("modelMapper");
            field.setAccessible(true);
            field.set(userService, modelMapper);
        } catch (Exception e) {
            fail("No se pudo inyectar modelMapper en el servicio: " + e.getMessage());
        }

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
        Usuario usuarioMock = new Usuario();
        usuarioMock.setUsername("testuser");
        usuarioMock.setEmail("test@email.com");
        usuarioMock.setName("Test User");

        when(usuarioRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(usuarioMock));
        List<UserResponseDTO> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(usuarioRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void create_debeCrearUsuario_cuandoDatosValidos() throws Exception {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UserResponseDTO result = userService.create(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void create_debeLanzarExcepcion_cuandoEmailYaExiste() {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(requestDTO))
                .isInstanceOf(Exception.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void create_debeLanzarExcepcion_cuandoUsernameYaExiste() {
        when(usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(requestDTO.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(requestDTO))
                .isInstanceOf(Exception.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void findById_debeRetornarUsuario_cuandoExisteId() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UserResponseDTO result = userService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    void findById_debeLanzarExcepcion_cuandoNoExisteId() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findByEmail_debeRetornarUsuario_cuandoExisteEmail() throws Exception {
        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(Optional.of(usuario));

        UserResponseDTO result = userService.findByEmail("test@email.com");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void delete_debeEliminarUsuario_cuandoExiste() throws Exception {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_debeLanzarExcepcion_cuandoNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.delete(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void update_debeActualizarUsuario_cuandoExiste() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UserResponseDTO result = userService.update(1L, requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void update_debeLanzarExcepcion_cuandoNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(99L, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findSeguidoresByArtistaId_debeRetornarLista() {
        when(usuarioRepository.findSeguidoresByArtistaId(1L)).thenReturn(List.of(usuario));

        List<UserResponseDTO> result = userService.findSeguidoresByArtistaId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(usuarioRepository, times(1)).findSeguidoresByArtistaId(1L);
    }
}