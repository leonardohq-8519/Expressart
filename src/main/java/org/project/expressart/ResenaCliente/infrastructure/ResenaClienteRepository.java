package org.project.expressart.ResenaCliente.infrastructure;

import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaClienteRepository extends JpaRepository<ResenaCliente, Long> {
    List<ClientReviewResponseDTO> findAllBy(Pageable pageable);

    Optional<List<ClientReviewResponseDTO>> findByClienteId(Long clienteId);

    Optional<List<ClientReviewResponseDTO>> findByArtistaId(Long artistaId);

    boolean existsByOrderId(Long orderId);
}
