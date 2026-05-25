package org.project.expressart.Mensaje.infrastructure;

import org.project.expressart.Mensaje.domain.Mensaje;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findAllBy(Pageable pageable);

    List<Mensaje> findByChatId(Long chatId);
}