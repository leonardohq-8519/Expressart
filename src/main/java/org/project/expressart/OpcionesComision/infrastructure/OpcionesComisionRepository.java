package org.project.expressart.OpcionesComision.infrastructure;

import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcionesComisionRepository extends JpaRepository<OpcionesComision, Long> {
    List<CommissionOptionsResponseDTO> findAllBy(Pageable pageable);

}