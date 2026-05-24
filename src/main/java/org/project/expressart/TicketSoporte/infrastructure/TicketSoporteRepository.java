package org.project.expressart.TicketSoporte.infrastructure;

import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {

    Optional<List<TicketSoporte>> findByUsuarioId(Long usuarioId);
    List<SupportTicketResponseDTO> findAllBy(Pageable pageable);
    Optional<List<TicketSoporte>> findByEstado(EstadoTicket estado);

    Optional<List<TicketSoporte>> findByEstadoAndCategoria(
            EstadoTicket estado,
            CategoriaTicket categoria
    );
}