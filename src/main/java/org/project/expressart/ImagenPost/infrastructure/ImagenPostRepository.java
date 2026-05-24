package org.project.expressart.ImagenPost.infrastructure;

import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenPostRepository extends JpaRepository<ImagenPost, Long> {

    List<ImagenPost> findByPostId(Long postId);

    List<ImagenPost> findByPostIdOrderByOrdenAsc(Long postId);

    void deleteByPostId(Long postId);

    long countByPostId(Long postId);
    Boolean existsByPostId(Long postId);
}