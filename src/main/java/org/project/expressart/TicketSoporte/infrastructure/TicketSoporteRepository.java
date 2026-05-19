package org.project.expressart.TicketSoporte.infrastructure;

import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {
}