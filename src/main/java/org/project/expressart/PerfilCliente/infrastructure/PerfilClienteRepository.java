package org.project.expressart.PerfilCliente.infrastructure;

import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilClienteRepository extends JpaRepository<PerfilCliente, Long> {
    Optional<PerfilCliente> findByUsuarioId(Long usuarioId);
}