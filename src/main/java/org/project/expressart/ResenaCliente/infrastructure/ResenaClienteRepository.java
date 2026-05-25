package org.project.expressart.ResenaCliente.infrastructure;

import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaClienteRepository extends JpaRepository<ResenaCliente, Long> {

    List<ClientReviewResponseDTO> findAllBy(Pageable pageable);

    @Query("SELECT r FROM ResenaCliente r WHERE r.cliente.id = :clienteId")
    List<ResenaCliente> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT r FROM ResenaCliente r WHERE r.artista.id = :artistaId")
    List<ResenaCliente> findByArtistaId(@Param("artistaId") Long artistaId);

    @Query("SELECT r FROM ResenaCliente r WHERE r.orden.id = :orderId")
    boolean existsByOrderId(@Param("orderId") Long orderId);
}