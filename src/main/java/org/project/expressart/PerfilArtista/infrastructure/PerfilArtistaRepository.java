package org.project.expressart.PerfilArtista.infrastructure;

import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Long> {
    Optional<PerfilArtista> findByUsuarioId(Long usuarioId);
}