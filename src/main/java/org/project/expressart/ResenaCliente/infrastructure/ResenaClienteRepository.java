package org.project.expressart.ResenaCliente.infrastructure;

import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaClienteRepository extends JpaRepository<ResenaCliente, Long> {

    List<ResenaCliente> findByClienteId(Long clienteId);

    List<ResenaCliente> findByArtistaId(Long artistaId);

    boolean existsByOrderId(Long orderId);
}
