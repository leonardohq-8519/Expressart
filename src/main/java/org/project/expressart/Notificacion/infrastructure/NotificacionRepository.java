package org.project.expressart.Notificacion.infrastructure;

import org.project.expressart.Notificacion.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :usuarioId")
    List<Notificacion> findByUsuarioUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :usuarioId AND n.leida = :leida")
    List<Notificacion> findByUsuarioUsuarioIdAndLeida(@Param("usuarioId") Long usuarioId, @Param("leida") boolean leida);

    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.usuario.id = :usuarioId AND n.leida = :leida")
    long countByUsuarioUsuarioIdAndLeida(@Param("usuarioId") Long usuarioId, @Param("leida") boolean leida);
}