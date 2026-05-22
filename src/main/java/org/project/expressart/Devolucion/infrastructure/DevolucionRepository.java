package org.project.expressart.Devolucion.infrastructure;

import org.project.expressart.Devolucion.domain.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
}