package org.project.expressart.ArchivoPost.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.ArchivoPost.domain.ArchivoPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArchivoPostRepositoryTest {

    @Autowired
    private ArchivoPostRepository archivoPostRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<ArchivoPost> result = archivoPostRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByPostIdAndPostNotExists() {
        List<ArchivoPost> result = archivoPostRepository.findByPostId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenExistsByPostIdAndNotExists() {
        boolean exists = archivoPostRepository.existsByPostId(999L);
        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnFalseWhenExistsByIdAndNotExists() {
        boolean exists = archivoPostRepository.existsById(999L);
        assertThat(exists).isFalse();
    }
}
