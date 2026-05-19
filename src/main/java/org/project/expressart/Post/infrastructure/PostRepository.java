package org.project.expressart.Post.infrastructure;

import org.project.expressart.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}