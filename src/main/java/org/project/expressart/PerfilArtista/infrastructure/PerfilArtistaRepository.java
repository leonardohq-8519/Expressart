package org.project.expressart.PerfilArtista.infrastructure;

import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Long> {
    List<ArtistProfileResponseDTO> findAllBy(Pageable pageable);
    Optional<PerfilArtista> findByUsuarioId(Long userId);

}
