package org.project.expressart.Chat.infrastructure;

import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<ChatResponseDTO> findAllBy(Pageable pageable);

    Optional<Chat> findByOrderId(Long orderId);

    Optional<Chat> findByRedisChannel(String redisChannel);
}