package org.project.expressart.Tags.infrastructure;

import org.project.expressart.Tags.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {

    Optional<Tags> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}