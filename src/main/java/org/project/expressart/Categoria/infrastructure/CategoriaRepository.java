package org.project.expressart.Categoria.infrastructure;

import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<CategoryResponseDTO> findAllBy(Pageable pageable);

    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
