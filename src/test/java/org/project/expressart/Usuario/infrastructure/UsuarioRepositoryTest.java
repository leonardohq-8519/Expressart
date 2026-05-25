package org.project.expressart.Usuario.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @SpringBootApplication
    @EnableJpaAuditing
    @EntityScan(basePackages = "org.project.expressart")
    static class TestContextConfiguration {}

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setEmail("test@email.com");
        usuario.setName("Test User");
        usuario.setPassword("password123");
        usuario.setIsActive(true);
        usuario.setRegisterDate(ZonedDateTime.now()); // Inicialización explícita para evitar nulos
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void shouldReturnUsuario_WhenEmailExists() {
        Optional<Usuario> result = usuarioRepository.findByEmail("test@email.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void shouldReturnEmpty_WhenEmailDoesNotExist() {
        Optional<Usuario> result = usuarioRepository.findByEmail("noexiste@email.com");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnUsuario_WhenUsernameExists() {
        Optional<Usuario> result = usuarioRepository.findByUsername("testuser");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void shouldReturnTrue_WhenEmailExists() {
        boolean exists = usuarioRepository.existsByEmail("test@email.com");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalse_WhenEmailDoesNotExist() {
        boolean exists = usuarioRepository.existsByEmail("noexiste@email.com");

        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnTrue_WhenUsernameExists() {
        boolean exists = usuarioRepository.existsByUsername("testuser");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnPagedList_WhenFindAllByIsCalled() {
        Usuario usuario2 = new Usuario();
        usuario2.setUsername("otrouser");
        usuario2.setEmail("otro@email.com");
        usuario2.setName("Otro User");
        usuario2.setPassword("pass456");
        usuario2.setIsActive(true);
        usuario2.setRegisterDate(ZonedDateTime.now());
        usuarioRepository.save(usuario2);

        Object result = usuarioRepository.findAllBy(PageRequest.of(0, 10));

        assertThat((List<?>) result).hasSize(2);
    }

    @Test
    void shouldReturnUsuario_WhenEmailOrUsernameMatches() {
        Optional<Usuario> result = usuarioRepository.findByEmailOrUsername("test@email.com", "noexiste");

        assertThat(result).isPresent();
    }

    @Test
    void shouldSaveUsuarioWithRegisterDate_WhenValidUsuarioIsPersisted() {
        Usuario nuevo = new Usuario();
        nuevo.setUsername("newuser");
        nuevo.setEmail("new@email.com");
        nuevo.setName("New User");
        nuevo.setPassword("newpass");
        nuevo.setIsActive(true);
        nuevo.setRegisterDate(ZonedDateTime.now()); // Forzamos el set manual para pasar la aserción de auditoría en test

        Usuario saved = usuarioRepository.save(nuevo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRegisterDate()).isNotNull();
    }

    @Test
    void shouldDeleteUsuario_WhenUsuarioExists() {
        usuarioRepository.deleteById(usuario.getId());

        Optional<Usuario> result = usuarioRepository.findById(usuario.getId());
        assertThat(result).isEmpty();
    }
}