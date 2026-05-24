package org.project.expressart.ArchivoPost.infrastructure;

import org.project.expressart.ArchivoPost.domain.ArchivoPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoPostRepository extends JpaRepository<ArchivoPost, Long> {
    List<ArchivoPost> findByPostId(Long postId);

    boolean existsByPostId(Long postId);

    void deleteByPostId(Long postId);
}
