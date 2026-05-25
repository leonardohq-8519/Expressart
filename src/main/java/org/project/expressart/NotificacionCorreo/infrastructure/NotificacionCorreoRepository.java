package org.project.expressart.NotificacionCorreo.infrastructure;

import org.project.expressart.Notificacion.domain.Notificacion;
import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.NotificacionCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionCorreoRepository extends JpaRepository<NotificacionCorreo, Long> {

    List<NotificacionCorreo> findByEstado(EstadoCorreo estado);

    List<NotificacionCorreo> findByUsuarioId(Long usuarioId);

}