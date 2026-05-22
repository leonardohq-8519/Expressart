package org.project.expressart.Comision.infrastructure;

import org.project.expressart.Comision.domain.Comision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComisionRepository extends JpaRepository<Comision, Long> {
}