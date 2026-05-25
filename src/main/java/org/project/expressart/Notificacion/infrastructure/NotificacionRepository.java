package org.project.expressart.Notificacion.infrastructure;

import org.project.expressart.Notificacion.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioId(Long usuarioId);

    List<Notificacion> findByUsuarioIdAndLeida(Long usuarioId, Boolean leida);

    long countByUsuarioIdAndLeida(Long usuarioId, Boolean leida);
}