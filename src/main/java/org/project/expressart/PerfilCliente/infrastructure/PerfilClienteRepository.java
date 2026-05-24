package org.project.expressart.PerfilCliente.infrastructure;

import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerfilClienteRepository extends JpaRepository<PerfilCliente, Long> {
    List<ClientProfileResponseDTO> findAllBy(Pageable pageable);
    Optional<PerfilCliente> findByUsuarioId(Long userId);
}
