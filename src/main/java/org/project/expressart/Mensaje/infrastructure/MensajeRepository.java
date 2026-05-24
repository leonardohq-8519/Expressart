package org.project.expressart.Mensaje.infrastructure;

import org.project.expressart.Mensaje.domain.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByChatId(Long chatId);
}