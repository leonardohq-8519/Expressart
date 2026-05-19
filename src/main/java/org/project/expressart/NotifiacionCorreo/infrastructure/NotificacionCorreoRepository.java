package org.project.expressart.NotifiacionCorreo.infrastructure;

import org.project.expressart.NotifiacionCorreo.domain.NotificacionCorreo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionCorreoRepository extends JpaRepository<NotificacionCorreo, Long> {
}
