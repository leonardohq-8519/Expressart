package org.project.expressart.ImagenComision.infrastructure;

import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenComisionRepository extends JpaRepository<ImagenComision, Long> {
    List<ImagenComision> findByCommissionId(Long commissionId);
    Boolean existsByCommissionId(Long commId);
    void deleteByCommissionId(Long commId);

}
