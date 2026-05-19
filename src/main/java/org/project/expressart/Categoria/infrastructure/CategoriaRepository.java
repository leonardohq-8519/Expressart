package org.project.expressart.Categoria.infrastructure;

import org.project.expressart.Categoria.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
