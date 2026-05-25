package org.project.expressart.NotificacionCorreo.infrastructure;

import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.NotificacionCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionCorreoRepository extends JpaRepository<NotificacionCorreo, Long> {

    List<NotificacionCorreo> findByEstado(EstadoCorreo estado);

    @Query("""
        SELECT n FROM NotificacionCorreo n
        WHERE n.estado = 'FALLIDO'
        AND n.intentos < :maxIntentos
        ORDER BY n.fechaCreacion ASC
    """)

    List<NotificacionCorreo> findCorreosPendientesDeReintento(@Param("maxIntentos") Integer maxIntentos);

    List<NotificacionCorreo> findByUserId(Long userId);
}
