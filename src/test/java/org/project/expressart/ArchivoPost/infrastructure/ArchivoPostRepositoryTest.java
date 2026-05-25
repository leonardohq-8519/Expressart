package org.project.expressart.ArchivoPost.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.ArchivoPost.domain.ArchivoPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ArchivoPostRepositoryTest {

    @Autowired
    private ArchivoPostRepository archivoPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando el archivo de post no existe")
    void shouldReturnEmptyWhenArchivoPostNotFound() {
        Optional<ArchivoPost> result = archivoPostRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay archivos de post")
    void shouldReturnEmptyListWhenNoArchivosPosts() {
        List<ArchivoPost> result = archivoPostRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar archivo de post por ID")
    void shouldSaveAndFindArchivoPostById_WhenValidData() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (400, 'Artista400', 'artista400', 'artista400@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (400, true, NOW(), 0, false, 0, 400)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO portafolio (id, es_publico, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (400, true, NOW(), 'Portafolio 400', 400)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO posts (id, es_publico, fecha_publicacion, titulo, portafolio_id) " +
                        "VALUES (400, true, NOW(), 'Post 400', 400)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO archivos_post (id, url, orden_visualizacion, post_id) " +
                        "VALUES (400, 'http://example.com/archivo400.zip', 1, 400)")
                .executeUpdate();

        entityManager.clear();

        Optional<ArchivoPost> found = archivoPostRepository.findById(400L);
        assertThat(found).isPresent();
        assertThat(found.get().getUrl()).isEqualTo("http://example.com/archivo400.zip");
        assertThat(found.get().getOrdenVisualizacion()).isEqualTo(1);
    }

    @Test
    @DisplayName("Debería eliminar un archivo de post existente")
    void shouldDeleteArchivoPostWhenExists() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (401, 'Artista401', 'artista401', 'artista401@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (401, true, NOW(), 0, false, 0, 401)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO portafolio (id, es_publico, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (401, true, NOW(), 'Portafolio 401', 401)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO posts (id, es_publico, fecha_publicacion, titulo, portafolio_id) " +
                        "VALUES (401, true, NOW(), 'Post 401', 401)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO archivos_post (id, url, orden_visualizacion, post_id) " +
                        "VALUES (401, 'http://example.com/archivo401.zip', 1, 401)")
                .executeUpdate();

        entityManager.clear();

        archivoPostRepository.deleteById(401L);
        assertThat(archivoPostRepository.findById(401L)).isEmpty();
    }
}