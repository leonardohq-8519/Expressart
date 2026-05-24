package org.project.expressart.Post.infrastructure;

import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<PostResponseDTO> findAllBy(Pageable pageable);
    List<Post> findByPortafolioId(Long portafolioId);

    List<Post> findByPortafolioIdAndEsPublico(Long portafolioId, Boolean esPublico);

    @Query("""
        SELECT p FROM Post p
        JOIN p.categorias c
        WHERE c.id = :categoriaId
        AND p.esPublico = true
        ORDER BY p.fechaPublicacion DESC
    """)
    List<Post> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("""
        SELECT p FROM Post p
        JOIN p.tags t
        WHERE t.id = :tagId
        AND p.esPublico = true
        ORDER BY p.fechaPublicacion DESC
    """)
    List<Post> findByTagId(@Param("tagId") Long tagId);
}