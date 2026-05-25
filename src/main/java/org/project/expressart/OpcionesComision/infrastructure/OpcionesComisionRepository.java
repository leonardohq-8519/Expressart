package org.project.expressart.OpcionesComision.infrastructure;

import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionesComisionRepository extends JpaRepository<OpcionesComision, Long> {
    List<CommissionOptionsResponseDTO> findAllBy(Pageable pageable);
    List<OpcionesComision> findByComisionId(Long commissionId);

}