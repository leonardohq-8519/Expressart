package org.project.expressart.Categoria.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
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
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debería guardar y encontrar una categoría por ID")
    void shouldSaveAndFindCategoriaById_WhenValidData() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Pintura Digital");
        categoria.setDescripcion("Arte digital creado con software");

        Categoria saved = categoriaRepository.save(categoria);
        entityManager.clear();

        Optional<Categoria> found = categoriaRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Pintura Digital");
    }

    @Test
    @DisplayName("Debería retornar vacío cuando la categoría no existe")
    void shouldReturnEmptyWhenCategoriaNotFound() {
        Optional<Categoria> found = categoriaRepository.findById(999L);
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Debería encontrar categoría por nombre cuando existe")
    void shouldFindCategoriaByNombre_WhenExists() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Ilustración");
        entityManager.persistAndFlush(categoria);
        entityManager.clear();

        Optional<Categoria> found = categoriaRepository.findByNombre("Ilustración");
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Ilustración");
    }

    @Test
    @DisplayName("Debería retornar vacío cuando el nombre no existe")
    void shouldReturnEmptyWhenNombreNotFound() {
        Optional<Categoria> found = categoriaRepository.findByNombre("NombreInexistente");
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar true cuando el nombre ya existe")
    void shouldReturnTrueWhenNombreAlreadyExists() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Escultura");
        entityManager.persistAndFlush(categoria);

        boolean exists = categoriaRepository.existsByNombre("Escultura");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Debería retornar false cuando el nombre no existe")
    void shouldReturnFalseWhenNombreDoesNotExist() {
        boolean exists = categoriaRepository.existsByNombre("CategoríaInexistente");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Debería retornar todas las categorías paginadas")
    void shouldReturnAllCategoriasWhenFindAllBy() {
        Categoria c1 = new Categoria();
        c1.setNombre("Fotografía");
        Categoria c2 = new Categoria();
        c2.setNombre("Cerámica");
        entityManager.persistAndFlush(c1);
        entityManager.persistAndFlush(c2);
        entityManager.clear();

        List<CategoryResponseDTO> result = categoriaRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(result).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("Debería eliminar una categoría existente")
    void shouldDeleteCategoriaWhenExists() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Acuarela");
        Categoria saved = entityManager.persistAndFlush(categoria);

        categoriaRepository.deleteById(saved.getId());

        assertThat(categoriaRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("Debería actualizar el nombre de una categoría existente")
    void shouldUpdateCategoriaWhenDataIsValid() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Arte Clásico");
        Categoria saved = entityManager.persistAndFlush(categoria);

        saved.setNombre("Arte Moderno");
        categoriaRepository.save(saved);
        entityManager.flush();
        entityManager.clear();

        Optional<Categoria> updated = categoriaRepository.findById(saved.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getNombre()).isEqualTo("Arte Moderno");
    }
}