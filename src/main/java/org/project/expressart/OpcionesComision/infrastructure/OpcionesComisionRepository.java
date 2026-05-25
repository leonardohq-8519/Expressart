package org.project.expressart.OpcionesComision.infrastructure;

import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpcionesComisionRepository extends JpaRepository<OpcionesComision, Long> {
    List<OpcionesComision> findAllBy(Pageable pageable);
    List<OpcionesComision> findByComisionId(Long commissionId);

}