package org.project.expressart.Notificacion.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Notificacion.domain.Notificacion;
import org.project.expressart.Notificacion.domain.TipoNotificacion;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Usuario crearUsuarioDePrueba() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@expressart.com");
        usuario.setFullName("Luciano Matias"); // Corregido a setFullName (con 'N' mayúscula)
        usuario.setUsername("lucianoma");
        usuario.setActive(true);              // Lombok mapea boolean "isActive" como setActive()
        usuario.setVerified(true);            // Lombok mapea boolean "isVerified" como setVerified()
        usuario.setRegisterDate(ZonedDateTime.now());
        return entityManager.persistAndFlush(usuario);
    }

    @Test
    void findByUsuarioIdAndLeida_DebeRetornarSoloRegistrosCorrespondientes() {
        Usuario usuarioGuardado = crearUsuarioDePrueba();

        Notificacion n1 = new Notificacion();
        n1.setUsuario(usuarioGuardado);
        n1.setTipo(TipoNotificacion.SISTEMA);
        n1.setTitulo("Notificacion 1");
        n1.setMensaje("Contenido");
        n1.setLeida(false);
        n1.setFechaCreacion(ZonedDateTime.now());
        entityManager.persist(n1);

        Notificacion n2 = new Notificacion();
        n2.setUsuario(usuarioGuardado);
        n2.setTipo(TipoNotificacion.ORDEN);
        n2.setTitulo("Notificacion 2");
        n2.setMensaje("Contenido 2");
        n2.setLeida(true);
        n2.setFechaCreacion(ZonedDateTime.now());
        entityManager.persist(n2);

        entityManager.flush();

        // Se asume que en Usuario el identificador es 'usuarioId'
        List<Notificacion> noLeidas = notificacionRepository.findByUsuarioIdAndLeida(usuarioGuardado.getId(), false);

        assertThat(noLeidas).hasSize(1);
        assertThat(noLeidas.getFirst().getTitulo()).isEqualTo("Notificacion 1");
    }

    @Test
    void countByUsuarioUsuarioIdAndLeida_DebeContarSoloNoLeidas() {
        Usuario usuarioGuardado = crearUsuarioDePrueba();

        Notificacion n1 = new Notificacion();
        n1.setUsuario(usuarioGuardado);
        n1.setTipo(TipoNotificacion.PAGO);
        n1.setTitulo("Pago realizado");
        n1.setMensaje("Ok");
        n1.setLeida(false);
        n1.setFechaCreacion(ZonedDateTime.now());
        entityManager.persistAndFlush(n1);

        long totalNoLeidas = notificacionRepository.countByUsuarioIdAndLeida(usuarioGuardado.getId(), false);

        assertThat(totalNoLeidas).isEqualTo(1L);
    }
}