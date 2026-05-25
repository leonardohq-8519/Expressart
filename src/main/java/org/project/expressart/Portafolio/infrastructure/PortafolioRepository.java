package org.project.expressart.Portafolio.infrastructure;

import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortafolioRepository extends JpaRepository<Portafolio, Long> {
    List<PortafolioResponseDTO> findAllBy(Pageable pageable);

    List<Portafolio> findByPerfilArtistaId(Long perfilArtistaId);

    List<Portafolio> findByPerfilArtistaIdAndEsPublico(Long perfilArtistaId, Boolean esPublico);
}