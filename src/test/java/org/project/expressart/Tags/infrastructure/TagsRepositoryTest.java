package org.project.expressart.Tags.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Tags.domain.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagsRepository tagsRepository;

    @Test
    void save_debePersistirTagCorrectamente() {
        Tags nuevoTag = new Tags();
        nuevoTag.setNombre("Escultura");

        Tags tagGuardado = tagsRepository.save(nuevoTag);

        assertThat(tagGuardado).isNotNull();
        assertThat(tagGuardado.getId()).isNotNull();
        assertThat(tagGuardado.getNombre()).isEqualTo("Escultura");
    }

    @Test
    void findById_debeRetornarTagPersistido() {
        Tags tagDummy = new Tags();
        tagDummy.setNombre("Fotografia");
        tagDummy = entityManager.persistAndFlush(tagDummy);

        Optional<Tags> encontrado = tagsRepository.findById(tagDummy.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Fotografia");
    }
}