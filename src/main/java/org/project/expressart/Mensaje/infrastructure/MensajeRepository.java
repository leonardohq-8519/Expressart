package org.project.expressart.Mensaje.infrastructure;

import org.project.expressart.Mensaje.domain.Mensaje;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<MessageResponseDTO> findAllBy(Pageable pageable);
    List<Mensaje> findByChatId(Long chatId);

}

