package org.project.expressart.Tags.infrastructure;

import org.project.expressart.Tags.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Long> {
}