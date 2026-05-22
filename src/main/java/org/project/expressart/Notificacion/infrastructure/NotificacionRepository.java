package org.project.expressart.Notificacion.infrastructure;

import org.project.expressart.Notificacion.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
