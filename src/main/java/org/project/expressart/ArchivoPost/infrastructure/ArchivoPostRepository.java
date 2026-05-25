package org.project.expressart.ArchivoPost.infrastructure;

import org.project.expressart.ArchivoPost.domain.ArchivoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoPostRepository extends JpaRepository<ArchivoPost, Long> {
    List<ArchivoPost> findByPostId(Long postId);

    boolean existsByPostId(Long postId);

    void deleteByPostId(Long postId);
}
