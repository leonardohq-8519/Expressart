package org.project.expressart.CuentaOAuth.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CuentaOAuthRepositoryTest {

    @Autowired
    private CuentaOAuthRepository cuentaOAuthRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<CuentaOAuth> result = cuentaOAuthRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldPersistAndFindCuentaOAuthWhenSave() {
        Usuario user = new Usuario();
        user.setUsername("oauth_user");
        user.setEmail("oauth@test.com");
        user.setName("OAuth User");
        user.setPassword("pass");
        user.setRegisterDate(ZonedDateTime.now());
        user.setIsActive(true);
        user.setIsVerified(true);
        Usuario savedUser = usuarioRepository.save(user);

        CuentaOAuth cuenta = new CuentaOAuth();
        cuenta.setUsuario(savedUser);
        cuenta.setProveedor("google");
        cuenta.setProveedorId("google-sub-123");
        cuenta.setEmailOauth("oauth@test.com");

        CuentaOAuth saved = cuentaOAuthRepository.save(cuenta);

        assertThat(saved.getId()).isNotNull();
        assertThat(cuentaOAuthRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void shouldDeleteCuentaOAuthWhenDeleteById() {
        Usuario user = new Usuario();
        user.setUsername("oauth_del_user");
        user.setEmail("oauth_del@test.com");
        user.setName("Del User");
        user.setPassword("pass");
        user.setRegisterDate(ZonedDateTime.now());
        user.setIsActive(true);
        user.setIsVerified(true);
        Usuario savedUser = usuarioRepository.save(user);

        CuentaOAuth cuenta = new CuentaOAuth();
        cuenta.setUsuario(savedUser);
        cuenta.setProveedor("google");
        cuenta.setProveedorId("google-sub-456");

        CuentaOAuth saved = cuentaOAuthRepository.save(cuenta);
        cuentaOAuthRepository.deleteById(saved.getId());

        assertThat(cuentaOAuthRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenExistsByIdAndNotExists() {
        assertThat(cuentaOAuthRepository.existsById(999L)).isFalse();
    }
}
