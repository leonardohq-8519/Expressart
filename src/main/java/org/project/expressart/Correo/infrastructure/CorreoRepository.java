package org.project.expressart.Correo.infrastructure;

import org.project.expressart.Correo.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreoRepository extends JpaRepository<Correo, Long> {
}