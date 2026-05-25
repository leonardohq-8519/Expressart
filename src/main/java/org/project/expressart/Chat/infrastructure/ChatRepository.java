package org.project.expressart.Chat.infrastructure;

import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Chat.dto.ChatResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<ChatResponseDTO> findAllBy(Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE c.orden.id = :orderId")
    Optional<Chat> findByOrderId(@Param("orderId") Long orderId);

    @Query(value = "SELECT * FROM chat WHERE redis_channel = :redisChannel", nativeQuery = true)
    Optional<Chat> findByRedisChannel(@Param("redisChannel") String redisChannel);
}