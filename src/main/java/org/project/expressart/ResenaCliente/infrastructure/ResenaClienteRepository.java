package org.project.expressart.ResenaCliente.infrastructure;

import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResenaClienteRepository extends JpaRepository<ResenaCliente, Long> {
}
