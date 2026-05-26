package org.project.expressart.CuentaOAuth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthUpdateDTO;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthAccountServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private PerfilClienteRepository clientProfileRepository;

    @InjectMocks
    private OAuthAccountService oAuthAccountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnEmptyListWhenGetByUserWithNoAccounts() {
        List<CuentaOAuthResponseDTO> result = oAuthAccountService.getByUser(1L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyResponseWhenGetByIdCalled() {
        CuentaOAuthResponseDTO result = oAuthAccountService.getById(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldReturnEmptyResponseWhenCreateCalled() {
        CuentaOAuthCreateDTO dto = new CuentaOAuthCreateDTO(1L, "google", "sub-123", "test@gmail.com");
        CuentaOAuthResponseDTO result = oAuthAccountService.create(dto);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldReturnEmptyResponseWhenUpdateCalled() {
        CuentaOAuthUpdateDTO dto = new CuentaOAuthUpdateDTO("updated@gmail.com");
        CuentaOAuthResponseDTO result = oAuthAccountService.update(1L, dto);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowWhenExtractDataCalledWithUnsupportedProvider() {
        assertThatThrownBy(() -> oAuthAccountService.processOAuthLogin(null, "facebook"))
                .isInstanceOf(Exception.class);
    }

    @Test
    void shouldReturnExistingUserWhenProcessOAuthLoginAndUserAlreadyLinked() {
        Usuario existingUser = new Usuario();
        existingUser.setId(1L);

        when(userRepository.findByOAuthProveedorAndProveedorId("google", "sub-123"))
                .thenReturn(Optional.of(existingUser));

        org.springframework.security.oauth2.core.user.OAuth2User oAuth2User =
                mock(org.springframework.security.oauth2.core.user.OAuth2User.class);
        when(oAuth2User.getAttribute("sub")).thenReturn("sub-123");
        when(oAuth2User.getAttribute("email")).thenReturn("test@gmail.com");
        when(oAuth2User.getAttribute("name")).thenReturn("Test User");
        when(oAuth2User.getAttribute("picture")).thenReturn(null);

        Usuario result = oAuthAccountService.processOAuthLogin(oAuth2User, "google");

        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository, never()).save(any());
    }
}
