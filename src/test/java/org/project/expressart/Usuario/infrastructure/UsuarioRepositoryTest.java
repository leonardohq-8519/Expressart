package org.project.expressart.Usuario.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @SpringBootApplication
    @EnableJpaAuditing
    @EntityScan(basePackages = "org.project.expressart") // <- Cambiado aquí
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
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void findByEmail_debeRetornarUsuario_cuandoExisteEmail() {
        Optional<Usuario> result = usuarioRepository.findByEmail("test@email.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@email.com");
    }

    @Test
    void findByEmail_debeRetornarVacio_cuandoNoExisteEmail() {
        Optional<Usuario> result = usuarioRepository.findByEmail("noexiste@email.com");

        assertThat(result).isEmpty();
    }

    @Test
    void findByUsername_debeRetornarUsuario_cuandoExisteUsername() {
        Optional<Usuario> result = usuarioRepository.findByUsername("testuser");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void existsByEmail_debeRetornarTrue_cuandoExisteEmail() {
        boolean exists = usuarioRepository.existsByEmail("test@email.com");

        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmail_debeRetornarFalse_cuandoNoExisteEmail() {
        boolean exists = usuarioRepository.existsByEmail("noexiste@email.com");

        assertThat(exists).isFalse();
    }

    @Test
    void existsByUsername_debeRetornarTrue_cuandoExisteUsername() {
        boolean exists = usuarioRepository.existsByUsername("testuser");

        assertThat(exists).isTrue();
    }

    @Test
    void findAllBy_debeRetornarListaPaginada() {
        Usuario usuario2 = new Usuario();
        usuario2.setUsername("otrouser");
        usuario2.setEmail("otro@email.com");
        usuario2.setName("Otro User");
        usuario2.setPassword("pass456");
        usuario2.setIsActive(true);
        usuarioRepository.save(usuario2);

        Object result = usuarioRepository.findAllBy(PageRequest.of(0, 10));

        assertThat((List<?>) result).hasSize(2);
    }

    @Test
    void findByEmailOrUsername_debeRetornarUsuario_cuandoMatchEmail() {
        Optional<Usuario> result = usuarioRepository.findByEmailOrUsername("test@email.com", "noexiste");

        assertThat(result).isPresent();
    }

    @Test
    void save_debeGuardarUsuario_conFechaRegistroAutomatica() {
        Usuario nuevo = new Usuario();
        nuevo.setUsername("newuser");
        nuevo.setEmail("new@email.com");
        nuevo.setName("New User");
        nuevo.setPassword("newpass");
        nuevo.setIsActive(true);

        Usuario saved = usuarioRepository.save(nuevo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRegisterDate()).isNotNull();
    }

    @Test
    void delete_debeEliminarUsuario_cuandoExiste() {
        usuarioRepository.deleteById(usuario.getId());

        Optional<Usuario> result = usuarioRepository.findById(usuario.getId());
        assertThat(result).isEmpty();
    }
}