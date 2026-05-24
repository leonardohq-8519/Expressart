package org.project.expressart.PerfilArtista.infrastructure;

import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Long> {
    List<ArtistReviewResponseDTO> findAllBy(Pageable pageable);

}
