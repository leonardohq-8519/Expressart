package org.project.expressart.Pago.infrastructure;

import org.project.expressart.Pago.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}