package org.project.expressart.Comision.infrastructure;

import org.project.expressart.Comision.domain.Comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComisionRepository extends JpaRepository<Comision, Long> {

    List<Comision> findByPerfilArtistaId(Long perfilArtistaId);
    List<Comision> findByPerfilArtistaIdAndEstaActiva(Long perfilArtistaId, Boolean estaActiva);

    @Query("""
        SELECT c FROM Comision c
        JOIN c.categorias cat
        WHERE cat.id = :categoriaId
        AND c.estaActiva = true
        ORDER BY c.fechaCreacion DESC
    """)
    List<Comision> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("""
        SELECT c FROM Comision c
        JOIN c.tags t
        WHERE t.id = :tagsId
        AND c.estaActiva = true
        ORDER BY c.fechaCreacion DESC
    """)
    List<Comision> findByTagsId(@Param("tagsId") Long tagsId);


}