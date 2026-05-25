package org.project.expressart.Comision.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Comision.domain.Comision;
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
class ComisionRepositoryTest {

    @Autowired
    private ComisionRepository comisionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la comisión no existe")
    void shouldReturnEmptyWhenComisionNotFound() {
        Optional<Comision> result = comisionRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay comisiones para el artista")
    void shouldReturnEmptyListWhenNoComisionesForArtista() {
        List<Comision> result = comisionRepository.findByPerfilArtistaId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay comisiones activas para el artista")
    void shouldReturnEmptyListWhenNoActiveComisionesForArtista() {
        List<Comision> result = comisionRepository.findByPerfilArtistaIdAndEstaActiva(999L, true);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay comisiones para la categoría")
    void shouldReturnEmptyListWhenNoComisionesForCategoria() {
        List<Comision> result = comisionRepository.findByCategoriaId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay comisiones para el tag")
    void shouldReturnEmptyListWhenNoComisionesForTag() {
        List<Comision> result = comisionRepository.findByTagsId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista paginada vacía cuando no hay comisiones")
    void shouldReturnEmptyPageWhenNoComisiones() {
        List<Comision> result = comisionRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y recuperar una comisión usando SQL nativo")
    void shouldSaveAndFindComisionWhenPersistedWithNativeSQL() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (100, 'Artista', 'artista100', 'artista100@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (100, true, NOW(), 0, false, 0, 100)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO comisiones (id, descripcion, esta_activa, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (100, 'Descripcion test', true, NOW(), 'Comisión Test', 100)")
                .executeUpdate();

        entityManager.clear();

        Optional<Comision> found = comisionRepository.findById(100L);
        assertThat(found).isPresent();
        assertThat(found.get().getTitulo()).isEqualTo("Comisión Test");
    }
}