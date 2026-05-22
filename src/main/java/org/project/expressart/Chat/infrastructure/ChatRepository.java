package org.project.expressart.Chat.infrastructure;

import org.project.expressart.Chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}