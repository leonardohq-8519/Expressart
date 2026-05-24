package org.project.expressart.PerfilArtista.infrastructure;

import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Long> {
}
