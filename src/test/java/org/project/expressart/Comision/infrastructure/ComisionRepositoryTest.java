package org.project.expressart.Comision.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Comision.domain.Comision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ComisionRepositoryTest {

    @Autowired
    private ComisionRepository comisionRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<Comision> result = comisionRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindAllByAndNoRecords() {
        List<?> result = comisionRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByPerfilArtistaIdAndNotExists() {
        List<Comision> result = comisionRepository.findByPerfilArtistaId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByPerfilArtistaIdAndEstaActivaAndNotExists() {
        List<Comision> result = comisionRepository.findByPerfilArtistaIdAndEstaActiva(999L, true);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByCategoriaIdAndNotExists() {
        List<Comision> result = comisionRepository.findByCategoriaId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindByTagsIdAndNotExists() {
        List<Comision> result = comisionRepository.findByTagsId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenExistsByIdAndNotExists() {
        boolean exists = comisionRepository.existsById(999L);
        assertThat(exists).isFalse();
    }
}
