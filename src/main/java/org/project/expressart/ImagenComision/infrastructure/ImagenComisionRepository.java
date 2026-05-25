package org.project.expressart.ImagenComision.infrastructure;

import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenComisionRepository extends JpaRepository<ImagenComision, Long> {
    List<ImagenComision> findByComisionId(Long commissionId);
    Boolean existsByComisionId(Long commId);
    void deleteByComisionId(Long commId);

}
