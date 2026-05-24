package org.project.expressart.ResenaArtista.infrastructure;

import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaArtistaRepository extends JpaRepository<ResenaArtista, Long> {

    List<ResenaArtista> findByArtistaId(Long artistaId);

    List<ResenaArtista> findByClienteId(Long clienteId);
    List<ArtistReviewResponseDTO> findAllBy(Pageable pageable);

    boolean existsByOrderId(Long orderId);
}
