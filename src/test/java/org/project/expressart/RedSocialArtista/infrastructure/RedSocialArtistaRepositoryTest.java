package org.project.expressart.RedSocialArtista.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RedSocialArtistaRepositoryTest {

    @Autowired
    private RedSocialArtistaRepository redSocialArtistaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería retornar vacío cuando la red social no existe")
    void shouldReturnEmptyWhenRedSocialNotFound() {
        Optional<RedSocialArtista> result = redSocialArtistaRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay redes sociales para el artista")
    void shouldReturnEmptyListWhenNoRedesSocialesForArtista() {
        List<RedSocialArtista> result = redSocialArtistaRepository.findByPerfilArtistaId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar vacío cuando no existe la red social para el artista y plataforma")
    void shouldReturnEmptyWhenNoRedSocialForArtistaAndPlataforma() {
        Optional<RedSocialArtista> result = redSocialArtistaRepository.findByPerfilArtistaIdAndPlataforma(999L, "instagram");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería guardar y encontrar red social por ID")
    void shouldSaveAndFindRedSocialById_WhenValidData() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (500, 'Artista500', 'artista500', 'artista500@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (500, true, NOW(), 0, false, 0, 500)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO redes_sociales_artista (id, plataforma, url, perfil_artista_id) " +
                        "VALUES (500, 'instagram', 'https://instagram.com/artista500', 500)")
                .executeUpdate();

        entityManager.clear();

        Optional<RedSocialArtista> found = redSocialArtistaRepository.findById(500L);
        assertThat(found).isPresent();
        assertThat(found.get().getPlataforma()).isEqualTo("instagram");
        assertThat(found.get().getUrl()).isEqualTo("https://instagram.com/artista500");
    }

    @Test
    @DisplayName("Debería encontrar redes sociales por perfil artista ID")
    void shouldFindRedesSocialesByPerfilArtistaId_WhenExist() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (501, 'Artista501', 'artista501', 'artista501@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (501, true, NOW(), 0, false, 0, 501)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO redes_sociales_artista (id, plataforma, url, perfil_artista_id) " +
                        "VALUES (501, 'twitter', 'https://twitter.com/artista501', 501)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO redes_sociales_artista (id, plataforma, url, perfil_artista_id) " +
                        "VALUES (502, 'instagram', 'https://instagram.com/artista501', 501)")
                .executeUpdate();

        entityManager.clear();

        List<RedSocialArtista> result = redSocialArtistaRepository.findByPerfilArtistaId(501L);
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar red social existente")
    void shouldDeleteRedSocialWhenExists() {
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (id, name, username, email, is_active, is_verified) " +
                        "VALUES (503, 'Artista503', 'artista503', 'artista503@test.com', true, true)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (503, true, NOW(), 0, false, 0, 503)")
                .executeUpdate();

        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO redes_sociales_artista (id, plataforma, url, perfil_artista_id) " +
                        "VALUES (503, 'youtube', 'https://youtube.com/artista503', 503)")
                .executeUpdate();

        entityManager.clear();

        redSocialArtistaRepository.deleteById(503L);
        assertThat(redSocialArtistaRepository.findById(503L)).isEmpty();
    }
}