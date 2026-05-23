package org.project.expressart.NotificacionCorreo.infrastructure;

import org.project.expressart.NotificacionCorreo.domain.NotificacionCorreo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionCorreoRepository extends JpaRepository<NotificacionCorreo, Long> {
}
