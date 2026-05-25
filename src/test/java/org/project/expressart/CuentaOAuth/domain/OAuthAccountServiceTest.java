package org.project.expressart.CuentaOAuth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.infrastructure.CuentaOAuthRepository;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthAccountServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private PerfilClienteRepository clientProfileRepository;

    private OAuthAccountService oAuthAccountService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        oAuthAccountService = new OAuthAccountService(userRepository, clientProfileRepository);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setName("Test User");
        usuario.setUsername("testuser");
        usuario.setEmail("test@example.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(false);
    }

    @Test
    @DisplayName("Debería retornar lista vacía de cuentas OAuth por usuario")
    void shouldReturnEmptyListWhenGetByUser() {
        List<CuentaOAuthResponseDTO> result = oAuthAccountService.getByUser(1L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debería retornar DTO vacío al obtener cuenta OAuth por ID")
    void shouldReturnEmptyDtoWhenGetById() {
        CuentaOAuthResponseDTO result = oAuthAccountService.getById(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Debería ejecutar delete sin lanzar excepción")
    void shouldNotThrowWhenDeletingOAuthAccount() {
        assertDoesNotThrow(() -> oAuthAccountService.delete(1L));
    }

    @Test
    @DisplayName("Debería ejecutar deleteByUser sin lanzar excepción")
    void shouldNotThrowWhenDeletingOAuthAccountByUser() {
        assertDoesNotThrow(() -> oAuthAccountService.deleteByUser(1L));
    }

    @Test
    @DisplayName("Debería retornar usuario existente cuando ya tiene cuenta OAuth vinculada")
    void shouldReturnExistingUserWhenOAuthAccountAlreadyLinked() {
        usuario.setIsVerified(true);
        org.springframework.security.oauth2.core.user.OAuth2User oAuth2User =
                mock(org.springframework.security.oauth2.core.user.OAuth2User.class);
        when(oAuth2User.getAttribute("sub")).thenReturn("google_id_123");
        when(oAuth2User.getAttribute("email")).thenReturn("test@example.com");
        when(oAuth2User.getAttribute("name")).thenReturn("Test User");
        when(oAuth2User.getAttribute("picture")).thenReturn(null);
        when(userRepository.findByOAuthProveedorAndProveedorId("google", "google_id_123"))
                .thenReturn(Optional.of(usuario));

        Usuario result = oAuthAccountService.processOAuthLogin(oAuth2User, "google");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByOAuthProveedorAndProveedorId("google", "google_id_123");
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el proveedor OAuth no está soportado")
    void shouldThrowExceptionWhenProviderNotSupported() {
        org.springframework.security.oauth2.core.user.OAuth2User oAuth2User =
                mock(org.springframework.security.oauth2.core.user.OAuth2User.class);

        assertThrows(IllegalArgumentException.class,
                () -> oAuthAccountService.processOAuthLogin(oAuth2User, "twitter"));
    }

    @Test
    @DisplayName("Debería crear nuevo usuario cuando no existe cuenta OAuth ni email")
    void shouldCreateNewUserWhenOAuthAccountAndEmailNotFound() {
        org.springframework.security.oauth2.core.user.OAuth2User oAuth2User =
                mock(org.springframework.security.oauth2.core.user.OAuth2User.class);
        when(oAuth2User.getAttribute("sub")).thenReturn("new_google_id");
        when(oAuth2User.getAttribute("email")).thenReturn("newuser@example.com");
        when(oAuth2User.getAttribute("name")).thenReturn("New User");
        when(oAuth2User.getAttribute("picture")).thenReturn(null);

        when(userRepository.findByOAuthProveedorAndProveedorId("google", "new_google_id"))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(clientProfileRepository.save(any(PerfilCliente.class))).thenReturn(new PerfilCliente());

        Usuario result = oAuthAccountService.processOAuthLogin(oAuth2User, "google");

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(Usuario.class));
    }
}