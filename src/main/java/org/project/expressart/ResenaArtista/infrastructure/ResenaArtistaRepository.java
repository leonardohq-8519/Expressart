package org.project.expressart.ResenaArtista.infrastructure;

import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaArtistaRepository extends JpaRepository<ResenaArtista, Long> {

    List<ResenaArtista> findByArtistaId(Long artistaId);

    List<ResenaArtista> findByClienteId(Long clienteId);

    boolean existsByOrderId(Long orderId);
}