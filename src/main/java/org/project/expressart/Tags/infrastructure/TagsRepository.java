package org.project.expressart.Tags.infrastructure;

import org.project.expressart.Tags.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

    Optional<Tags> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}