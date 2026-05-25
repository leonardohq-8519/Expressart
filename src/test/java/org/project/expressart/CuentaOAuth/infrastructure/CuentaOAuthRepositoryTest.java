package org.project.expressart.CuentaOAuth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CuentaOAuthRepositoryTest {

    @Autowired
    private CuentaOAuthRepository cuentaOAuthRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la cuenta OAuth no existe")
    void shouldReturnEmptyWhenCuentaOAuthNotFound() {
        Optional<CuentaOAuth> result = cuentaOAuthRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar una cuenta OAuth por ID")
    void shouldSaveAndFindCuentaOAuthById_WhenValidData() {
        Usuario usuario = new Usuario();
        usuario.setName("Usuario OAuth");
        usuario.setUsername("usuario_oauth_test");
        usuario.setEmail("oauth_test@example.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        CuentaOAuth cuentaOAuth = new CuentaOAuth();
        cuentaOAuth.setUsuario(savedUsuario);
        cuentaOAuth.setProveedor("google");
        cuentaOAuth.setProveedorId("google_id_123");
        cuentaOAuth.setEmailOauth("oauth_test@gmail.com");

        CuentaOAuth saved = cuentaOAuthRepository.save(cuentaOAuth);
        entityManager.clear();

        Optional<CuentaOAuth> found = cuentaOAuthRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getProveedor()).isEqualTo("google");
        assertThat(found.get().getProveedorId()).isEqualTo("google_id_123");
    }

    @Test
    @DisplayName("Debería retornar todas las cuentas OAuth guardadas")
    void shouldReturnAllCuentasOAuthWhenMultipleSaved() {
        Usuario u1 = new Usuario();
        u1.setName("User1");
        u1.setUsername("user1_oauth");
        u1.setEmail("user1_oauth@test.com");
        u1.setIsActive(true);
        u1.setIsVerified(true);
        Usuario savedU1 = entityManager.persistAndFlush(u1);

        Usuario u2 = new Usuario();
        u2.setName("User2");
        u2.setUsername("user2_oauth");
        u2.setEmail("user2_oauth@test.com");
        u2.setIsActive(true);
        u2.setIsVerified(true);
        Usuario savedU2 = entityManager.persistAndFlush(u2);

        CuentaOAuth c1 = new CuentaOAuth();
        c1.setUsuario(savedU1);
        c1.setProveedor("google");
        c1.setProveedorId("google_id_u1");
        cuentaOAuthRepository.save(c1);

        CuentaOAuth c2 = new CuentaOAuth();
        c2.setUsuario(savedU2);
        c2.setProveedor("discord");
        c2.setProveedorId("discord_id_u2");
        cuentaOAuthRepository.save(c2);

        List<CuentaOAuth> all = cuentaOAuthRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar una cuenta OAuth existente")
    void shouldDeleteCuentaOAuthWhenExists() {
        Usuario usuario = new Usuario();
        usuario.setName("Delete User");
        usuario.setUsername("delete_oauth_user");
        usuario.setEmail("delete_oauth@test.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        CuentaOAuth cuentaOAuth = new CuentaOAuth();
        cuentaOAuth.setUsuario(savedUsuario);
        cuentaOAuth.setProveedor("google");
        cuentaOAuth.setProveedorId("google_delete_id");
        CuentaOAuth saved = cuentaOAuthRepository.save(cuentaOAuth);

        cuentaOAuthRepository.deleteById(saved.getId());

        assertThat(cuentaOAuthRepository.findById(saved.getId())).isEmpty();
    }
}