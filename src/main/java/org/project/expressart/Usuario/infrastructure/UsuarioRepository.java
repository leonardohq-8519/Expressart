package org.project.expressart.Usuario.infrastructure;

import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}