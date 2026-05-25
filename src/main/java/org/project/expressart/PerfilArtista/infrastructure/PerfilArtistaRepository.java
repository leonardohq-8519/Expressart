package org.project.expressart.PerfilArtista.infrastructure;

import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Long> {
    List<ArtistProfileResponseDTO> findAllBy(Pageable pageable);
    Optional<PerfilArtista> findByUsuarioId(Long userId);

}
