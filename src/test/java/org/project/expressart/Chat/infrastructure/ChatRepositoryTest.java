package org.project.expressart.Chat.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Chat.domain.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<Chat> result = chatRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByOrderIdAndOrderNotExists() {
        Optional<Chat> result = chatRepository.findByOrderId(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByRedisChannelAndNotExists() {
        Optional<Chat> result = chatRepository.findByRedisChannel("chat:999");
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenFindAllAndNoChatsExist() {
        List<Chat> result = chatRepository.findAll();
        assertThat(result).isEmpty();
    }
}
