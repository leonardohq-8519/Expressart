package org.project.expressart.OpcionesComision.infrastructure;

import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OpcionesComisionRepository extends JpaRepository<OpcionesComision, Long> {
    List<OpcionesComision> findByComisionId(Long comisionId);
}