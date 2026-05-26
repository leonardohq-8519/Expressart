package org.project.expressart.Notificacion.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Notificacion.domain.Notificacion;
import org.project.expressart.Notificacion.domain.TipoNotificacion;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario savedUser;

    @BeforeEach
    void setUp() {
        Usuario user = new Usuario();
        user.setUsername("notif_user");
        user.setEmail("notif@test.com");
        user.setName("Notif User");
        user.setPassword("password");
        user.setRegisterDate(ZonedDateTime.now());
        user.setIsActive(true);
        user.setIsVerified(false);
        savedUser = usuarioRepository.save(user);
    }

    @Test
    void shouldReturnUnreadNotificationsWhenFindByUsuarioIdAndLeida() {
        Notificacion n1 = buildNotificacion(savedUser, "Notificacion 1", false);
        Notificacion n2 = buildNotificacion(savedUser, "Notificacion 2", true);
        notificacionRepository.save(n1);
        notificacionRepository.save(n2);

        List<Notificacion> noLeidas = notificacionRepository.findByUsuarioIdAndLeida(savedUser.getId(), false);

        assertThat(noLeidas).hasSize(1);
        assertThat(noLeidas.get(0).getTitulo()).isEqualTo("Notificacion 1");
    }

    @Test
    void shouldReturnAllNotificationsWhenFindByUsuarioId() {
        notificacionRepository.save(buildNotificacion(savedUser, "N1", false));
        notificacionRepository.save(buildNotificacion(savedUser, "N2", true));

        List<Notificacion> all = notificacionRepository.findByUsuarioId(savedUser.getId());

        assertThat(all).hasSize(2);
    }

    @Test
    void shouldCountUnreadNotificationsWhenCountByUsuarioIdAndLeida() {
        notificacionRepository.save(buildNotificacion(savedUser, "N1", false));
        notificacionRepository.save(buildNotificacion(savedUser, "N2", false));
        notificacionRepository.save(buildNotificacion(savedUser, "N3", true));

        long count = notificacionRepository.countByUsuarioIdAndLeida(savedUser.getId(), false);

        assertThat(count).isEqualTo(2L);
    }

    @Test
    void shouldReturnEmptyWhenFindByUsuarioIdAndNoNotificationsExist() {
        List<Notificacion> result = notificacionRepository.findByUsuarioId(999L);
        assertThat(result).isEmpty();
    }

    private Notificacion buildNotificacion(Usuario user, String titulo, boolean leida) {
        Notificacion n = new Notificacion();
        n.setUsuario(user);
        n.setTipo(TipoNotificacion.SISTEMA);
        n.setTitulo(titulo);
        n.setMensaje("Contenido de " + titulo);
        n.setLeida(leida);
        return n;
    }
}
