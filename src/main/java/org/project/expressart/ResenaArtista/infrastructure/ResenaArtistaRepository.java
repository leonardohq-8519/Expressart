package org.project.expressart.ResenaArtista.infrastructure;

import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResenaArtistaRepository extends JpaRepository<ResenaArtista, Long> {
}
