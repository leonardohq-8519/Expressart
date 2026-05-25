package org.project.expressart.NotificacionCorreo.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.NotificacionCorreo;
import org.project.expressart.NotificacionCorreo.domain.TipoCorreo;
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
class NotificacionCorreoRepositoryTest {

    @Autowired
    private NotificacionCorreoRepository notificacionCorreoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la notificación correo no existe")
    void shouldReturnEmptyWhenNotificacionCorreoNotFound() {
        Optional<NotificacionCorreo> result = notificacionCorreoRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay notificaciones por estado")
    void shouldReturnEmptyListWhenNoNotificacionesByEstado() {
        List<NotificacionCorreo> result = notificacionCorreoRepository.findByEstado(EstadoCorreo.FALLIDO);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay notificaciones para el usuario")
    void shouldReturnEmptyListWhenNoNotificacionesByUsuario() {
        List<NotificacionCorreo> result = notificacionCorreoRepository.findByUsuarioId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar notificación correo por ID")
    void shouldSaveAndFindNotificacionCorreoById_WhenValidData() {
        Usuario usuario = new Usuario();
        usuario.setName("Usuario Correo");
        usuario.setUsername("usuario_correo_test");
        usuario.setEmail("correo_test@example.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        NotificacionCorreo notif = new NotificacionCorreo();
        notif.setUsuario(savedUsuario);
        notif.setDestinatarioEmail("destino@example.com");
        notif.setAsunto("Asunto de prueba");
        notif.setTipo(TipoCorreo.NUEVA_ORDEN);
        notif.setEstado(EstadoCorreo.PENDIENTE);
        notif.setIntentos(0);

        NotificacionCorreo saved = notificacionCorreoRepository.save(notif);
        entityManager.clear();

        Optional<NotificacionCorreo> found = notificacionCorreoRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDestinatarioEmail()).isEqualTo("destino@example.com");
        assertThat(found.get().getEstado()).isEqualTo(EstadoCorreo.PENDIENTE);
    }

    @Test
    @DisplayName("Debería encontrar notificaciones pendientes por estado")
    void shouldFindNotificacionesByEstadoPendiente_WhenExist() {
        Usuario usuario = new Usuario();
        usuario.setName("Usuario Pendiente");
        usuario.setUsername("usuario_pendiente");
        usuario.setEmail("pendiente@example.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        NotificacionCorreo notif = new NotificacionCorreo();
        notif.setUsuario(savedUsuario);
        notif.setDestinatarioEmail("pendiente@test.com");
        notif.setAsunto("Pendiente");
        notif.setTipo(TipoCorreo.PAGO_CONFIRMADO);
        notif.setEstado(EstadoCorreo.PENDIENTE);
        notif.setIntentos(0);
        notificacionCorreoRepository.save(notif);
        entityManager.clear();

        List<NotificacionCorreo> result = notificacionCorreoRepository.findByEstado(EstadoCorreo.PENDIENTE);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEstado()).isEqualTo(EstadoCorreo.PENDIENTE);
    }

    @Test
    @DisplayName("Debería encontrar notificaciones por usuario ID")
    void shouldFindNotificacionesByUsuarioId_WhenExist() {
        Usuario usuario = new Usuario();
        usuario.setName("Usuario For Notif");
        usuario.setUsername("usuario_notif_test");
        usuario.setEmail("notif_test@example.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        NotificacionCorreo notif = new NotificacionCorreo();
        notif.setUsuario(savedUsuario);
        notif.setDestinatarioEmail("notif_test@example.com");
        notif.setAsunto("Asunto usuario");
        notif.setTipo(TipoCorreo.ORDEN_COMPLETADA);
        notif.setEstado(EstadoCorreo.ENVIADO);
        notif.setIntentos(1);
        notificacionCorreoRepository.save(notif);
        entityManager.clear();

        List<NotificacionCorreo> result = notificacionCorreoRepository.findByUsuarioId(savedUsuario.getId());
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("Debería eliminar notificación correo existente")
    void shouldDeleteNotificacionCorreoWhenExists() {
        Usuario usuario = new Usuario();
        usuario.setName("Delete User Correo");
        usuario.setUsername("delete_correo_user");
        usuario.setEmail("delete_correo@test.com");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        Usuario savedUsuario = entityManager.persistAndFlush(usuario);

        NotificacionCorreo notif = new NotificacionCorreo();
        notif.setUsuario(savedUsuario);
        notif.setDestinatarioEmail("delete@example.com");
        notif.setAsunto("Delete test");
        notif.setTipo(TipoCorreo.VERIFICACION_EMAIL);
        notif.setEstado(EstadoCorreo.PENDIENTE);
        notif.setIntentos(0);
        NotificacionCorreo saved = notificacionCorreoRepository.save(notif);

        notificacionCorreoRepository.deleteById(saved.getId());

        assertThat(notificacionCorreoRepository.findById(saved.getId())).isEmpty();
    }
}