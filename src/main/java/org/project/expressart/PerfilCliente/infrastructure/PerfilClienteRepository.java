package org.project.expressart.PerfilCliente.infrastructure;

import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilClienteRepository extends JpaRepository<PerfilCliente, Long> {
}
