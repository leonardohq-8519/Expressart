package org.project.expressart.Portafolio.infrastructure;

import org.project.expressart.Portafolio.domain.Portafolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortafolioRepository extends JpaRepository<Portafolio, Long> {
}