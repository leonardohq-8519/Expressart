package org.project.expressart.TicketSoporte.infrastructure;

import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {


    @Query("SELECT t FROM TicketSoporte t WHERE t.user.id = :usuarioId")
    List<TicketSoporte> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    List<SupportTicketResponseDTO> findAllBy(Pageable pageable);

    List<TicketSoporte> findByStatus(EstadoTicket status);

    List<TicketSoporte> findByStatusAndCategory(
            EstadoTicket status,
            CategoriaTicket category
    );
    default List<TicketSoporte> findByEstado(EstadoTicket status) {
        return findByStatus(status);
    }

    default List<TicketSoporte> findByEstadoAndCategoria(EstadoTicket status, CategoriaTicket category) {
        return findByStatusAndCategory(status, category);
    }
}