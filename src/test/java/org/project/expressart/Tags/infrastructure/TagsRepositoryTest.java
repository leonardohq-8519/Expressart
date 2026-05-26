package org.project.expressart.Tags.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Tags.domain.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagsRepositoryTest {

    @Autowired
    private TagsRepository tagsRepository;

    @Test
    void shouldPersistTagWhenSave() {
        Tags tag = new Tags();
        tag.setNombre("Escultura");

        Tags saved = tagsRepository.save(tag);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNombre()).isEqualTo("Escultura");
    }

    @Test
    void shouldReturnTagWhenFindByIdAndExists() {
        Tags tag = new Tags();
        tag.setNombre("Fotografia");
        tag = tagsRepository.save(tag);

        Optional<Tags> found = tagsRepository.findById(tag.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Fotografia");
    }

    @Test
    void shouldReturnTagWhenFindByNombreAndExists() {
        Tags tag = new Tags();
        tag.setNombre("Acuarela");
        tagsRepository.save(tag);

        Optional<Tags> found = tagsRepository.findByNombre("Acuarela");

        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Acuarela");
    }

    @Test
    void shouldReturnEmptyWhenFindByNombreAndNotExists() {
        Optional<Tags> found = tagsRepository.findByNombre("NoExiste");
        assertThat(found).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByNombreAndExists() {
        Tags tag = new Tags();
        tag.setNombre("Pintura");
        tagsRepository.save(tag);

        boolean exists = tagsRepository.existsByNombre("Pintura");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenExistsByNombreAndNotExists() {
        boolean exists = tagsRepository.existsByNombre("TagInexistente");
        assertThat(exists).isFalse();
    }

    @Test
    void shouldDeleteTagWhenDeleteById() {
        Tags tag = new Tags();
        tag.setNombre("Grabado");
        tag = tagsRepository.save(tag);
        Long id = tag.getId();

        tagsRepository.deleteById(id);

        assertThat(tagsRepository.findById(id)).isEmpty();
    }
}
