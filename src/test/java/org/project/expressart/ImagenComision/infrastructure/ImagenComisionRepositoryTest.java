package org.project.expressart.ImagenComision.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ImagenComisionRepositoryTest {

    @Autowired
    private ImagenComisionRepository imagenComisionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la imagen de comisión no existe")
    void shouldReturnEmptyWhenImagenComisionNotFound() {
        Optional<ImagenComision> result = imagenComisionRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay imágenes")
    void shouldReturnEmptyListWhenNoImagenes() {
        List<ImagenComision> result = imagenComisionRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar imagen de comisión por ID")
    void shouldSaveAndFindImagenComisionById_WhenNativeSQL() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (300, 'Artista300', 'artista300', 'artista300@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (300, true, NOW(), 0, false, 0, 300)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO comisiones (id, descripcion, esta_activa, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (300, 'Comision imagen test', true, NOW(), 'Comision Imagen 300', 300)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO imagenes_comision (id, url, orden, comision_id) " +
                        "VALUES (300, 'http://example.com/imagen.jpg', 1, 300)")
                .executeUpdate();

        entityManager.clear();

        Optional<ImagenComision> found = imagenComisionRepository.findById(300L);
        assertThat(found).isPresent();
        assertThat(found.get().getUrl()).isEqualTo("http://example.com/imagen.jpg");
        assertThat(found.get().getOrden()).isEqualTo(1);
    }

    @Test
    @DisplayName("Debería eliminar imagen de comisión existente")
    void shouldDeleteImagenComisionWhenExists() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (301, 'Artista301', 'artista301', 'artista301@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (301, true, NOW(), 0, false, 0, 301)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO comisiones (id, descripcion, esta_activa, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (301, 'Comision delete test', true, NOW(), 'Comision Delete 301', 301)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO imagenes_comision (id, url, orden, comision_id) " +
                        "VALUES (301, 'http://example.com/delete.jpg', 1, 301)")
                .executeUpdate();

        entityManager.clear();

        imagenComisionRepository.deleteById(301L);

        assertThat(imagenComisionRepository.findById(301L)).isEmpty();
    }
}