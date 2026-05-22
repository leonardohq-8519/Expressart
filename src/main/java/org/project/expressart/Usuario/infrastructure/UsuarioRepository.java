package org.project.expressart.Usuario.infrastructure;

import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmailOrUsername(String email, String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query("""
        SELECT u FROM Usuario AS u
        JOIN u.favoritos f
        WHERE f.id = :artistaId
        AND u.isActive = true
    """)
    List<Usuario> findSeguidoresByArtistaId(@Param("artistaId") Long artistaId);
}