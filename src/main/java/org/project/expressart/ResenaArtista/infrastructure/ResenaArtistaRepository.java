package org.project.expressart.ResenaArtista.infrastructure;

import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaArtistaRepository extends JpaRepository<ResenaArtista, Long> {

    @Query("SELECT r FROM ResenaArtista r WHERE r.artist.id = :artistaId")
    List<ResenaArtista> findByArtistaId(@Param("artistaId") Long artistaId);


    @Query("SELECT r FROM ResenaArtista r WHERE r.client.id = :clienteId")
    List<ResenaArtista> findByClienteId(@Param("clienteId") Long clienteId);

    List<ArtistReviewResponseDTO> findAllBy(Pageable pageable);

    @Query("SELECT COUNT(r) > 0 FROM ResenaArtista r WHERE r.order.id = :orderId")
    boolean existsByOrderId(@Param("orderId") Long orderId);
}