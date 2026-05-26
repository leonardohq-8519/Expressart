package org.project.expressart.ImagenComision.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ImagenComisionRepositoryTest {

    @Autowired
    private ImagenComisionRepository imagenComisionRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<ImagenComision> result = imagenComisionRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByComisionIdAndNotExists() {
        List<ImagenComision> result = imagenComisionRepository.findByComisionId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenExistsByComisionIdAndNotExists() {
        Boolean exists = imagenComisionRepository.existsByComisionId(999L);
        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnFalseWhenExistsByIdAndNotExists() {
        assertThat(imagenComisionRepository.existsById(999L)).isFalse();
    }
}
