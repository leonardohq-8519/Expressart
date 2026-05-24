package org.project.expressart.Devolucion.infrastructure;

import org.project.expressart.Devolucion.domain.Devolucion;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
    Optional<Devolucion> findByOrderId(Long orderId);

    List<Devolucion> findByEstado(EstadoDevolucion estado);
}