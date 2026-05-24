package org.project.expressart.Devolucion.infrastructure;

import org.project.expressart.Devolucion.domain.Devolucion;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Pago.domain.Pago;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
    Optional<Pago> findByOrderId(Long orderId);
    List<DevolutionResponseDTO> findAllBy(Pageable pageable);

    List<Devolucion> findByEstado(EstadoDevolucion estado);
}