package org.project.expressart.ImagenPost.infrastructure;

import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenPostRepository extends JpaRepository<ImagenPost, Long> {

    List<ImagenPost> findByPostId(Long postId);

    List<ImagenPost> findByPostIdOrderByOrdenAsc(Long postId);

    void deleteByPostId(Long postId);

    long countByPostId(Long postId);
}