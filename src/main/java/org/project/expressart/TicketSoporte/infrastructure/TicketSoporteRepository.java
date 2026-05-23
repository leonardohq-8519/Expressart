package org.project.expressart.TicketSoporte.infrastructure;

import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {

    List<TicketSoporte> findByUsuarioId(Long usuarioId);

    List<TicketSoporte> findByEstado(EstadoTicket estado);

    List<TicketSoporte> findByEstadoAndCategoria(
            EstadoTicket estado,
            CategoriaTicket categoria
    );
}