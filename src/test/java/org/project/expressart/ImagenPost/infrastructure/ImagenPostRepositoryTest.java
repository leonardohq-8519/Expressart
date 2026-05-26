package org.project.expressart.ImagenPost.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ImagenPostRepositoryTest {

    @Autowired
    private ImagenPostRepository imagenPostRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<ImagenPost> result = imagenPostRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByPostIdAndNotExists() {
        List<ImagenPost> result = imagenPostRepository.findByPostId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByPostIdOrderByOrdenAscAndNotExists() {
        List<ImagenPost> result = imagenPostRepository.findByPostIdOrderByOrdenAsc(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnZeroWhenCountByPostIdAndNotExists() {
        long count = imagenPostRepository.countByPostId(999L);
        assertThat(count).isZero();
    }

    @Test
    void shouldReturnFalseWhenExistsByIdAndNotExists() {
        assertThat(imagenPostRepository.existsById(999L)).isFalse();
    }
}
