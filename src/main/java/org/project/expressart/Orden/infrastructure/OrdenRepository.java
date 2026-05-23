package org.project.expressart.Orden.infrastructure;

import jakarta.persistence.criteria.Order;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenRepository, Long> {

        List<Order> findByClienteId(Long clienteId);

        List<Order> findByArtistaId(Long artistaId);

        List<Order> findByClienteIdAndEstado(Long clienteId, EstadoOrden estado);

        List<Order> findByArtistaIdAndEstado(Long artistaId, EstadoOrden estado);

        @Query("""
        SELECT o FROM Orden o
        WHERE o.fechaLimite < :fecha
        AND o.estado NOT IN :estadosExcluidos
        ORDER BY o.fechaLimite ASC
        """)
        List<Order> findByFechaLimiteBeforeAndEstadoNotIn(@Param("fecha") ZonedDateTime fecha, @Param("estadosExcluidos") List<EstadoOrden> estadosExcluidos
    );
}
