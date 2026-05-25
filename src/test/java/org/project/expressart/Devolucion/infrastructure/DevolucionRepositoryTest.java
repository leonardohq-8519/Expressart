package org.project.expressart.Devolucion.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Devolucion.domain.Devolucion;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DevolucionRepositoryTest {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la devolución no existe por ID")
    void shouldReturnEmptyWhenDevolucionNotFound() {
        Optional<Devolucion> result = devolucionRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar vacío cuando no existe devolución para la orden")
    void shouldReturnEmptyWhenDevolucionNotFoundByOrderId() {
        Optional<Devolucion> result = devolucionRepository.findByOrderId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay devoluciones por estado")
    void shouldReturnEmptyListWhenNoDevolucionesByEstado() {
        List<Devolucion> result = devolucionRepository.findByEstado(EstadoDevolucion.APROBADA);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista paginada vacía cuando no hay devoluciones")
    void shouldReturnEmptyPageWhenNoDevolucion() {
        List<Devolucion> result = devolucionRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar devolución por estado SOLICITADA")
    void shouldSaveAndFindDevolucionByEstadoSolicitada() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (200, 'Artista200', 'artista200', 'artista200@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (201, 'Cliente201', 'cliente201', 'cliente201@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (200, true, NOW(), 0, false, 0, 200)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO comisiones (id, descripcion, esta_activa, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (200, 'Comision test', true, NOW(), 'Comision Test 200', 200)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO opciones_comision (id, descripcion, esta_activa, incluye_archivo_fuente, nombre, numero_revisiones, precio, tiempo_entrega, comision_id) " +
                        "VALUES (200, 'Opcion test', true, false, 'Basica', 2, 50.00, 3, 200)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO orders (id, descripcion_trabajo, estado, fecha_creacion, fecha_limite, precio_final, artista_id, cliente_id, opcion_comision_id) " +
                        "VALUES (200, 'Trabajo test', 'PENDIENTE', NOW(), NOW() + 7, 50.00, 200, 201, 200)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO devoluciones (id, estado, motivo, monto_reembolso, fecha_solicitud, orden_id) " +
                        "VALUES (200, 'SOLICITADA', 'Motivo test', 50.00, NOW(), 200)")
                .executeUpdate();

        entityManager.clear();

        List<Devolucion> result = devolucionRepository.findByEstado(EstadoDevolucion.SOLICITADA);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEstado()).isEqualTo(EstadoDevolucion.SOLICITADA);
    }
}