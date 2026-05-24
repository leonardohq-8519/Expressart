package org.project.expressart.ArchivoPost.infrastructure;

import org.project.expressart.ArchivoPost.domain.ArchivoPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoPostRepository extends JpaRepository<ArchivoPost, Long> {
}
