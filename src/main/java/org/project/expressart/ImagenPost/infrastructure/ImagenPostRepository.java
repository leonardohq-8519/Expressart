package org.project.expressart.ImagenPost.infrastructure;

import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenPostRepository extends JpaRepository<ImagenPost, Long> {
}
