package org.project.expressart.Chat.infrastructure;

import org.project.expressart.Chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByOrderId(Long orderId);

    Optional<Chat> findByRedisChannel(String redisChannel);
}