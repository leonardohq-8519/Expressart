package org.project.expressart.Tags.infrastructure;

import org.project.expressart.Tags.domain.Tags;
import org.project.expressart.Tags.dto.TagsResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

    Optional<Tags> findByNombre(String nombre);
    List<TagsResponseDTO> findAllBy(Pageable pageable);


    boolean existsByNombre(String nombre);
}