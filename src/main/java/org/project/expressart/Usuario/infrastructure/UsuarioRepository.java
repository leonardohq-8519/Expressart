package org.project.expressart.Usuario.infrastructure;

import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmailOrUsername(String email, String username);
    List<Usuario> findAllBy(Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


    @Query("""
        SELECT u FROM Usuario AS u
        JOIN u.favorites f
        WHERE f.id = :artistaId
        AND u.isActive = true
    """)
    List<Usuario> findSeguidoresByArtistaId(@Param("artistaId") Long artistaId);


    @Query("""
        SELECT u FROM Usuario u
        JOIN u.OAuthAccounts c
        WHERE c.proveedor = :proveedor
        AND c.proveedorId = :proveedorId
    """)
    Optional<Usuario> findByOAuthProveedorAndProveedorId(
            @Param("proveedor") String proveedor,
            @Param("proveedorId") String proveedorId
    );
}