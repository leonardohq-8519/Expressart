package org.project.expressart.Orden.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class OrdenRepositoryTest {

    @Autowired
    private OrdenRepository ordenRepository;

    @Test
    void findByClienteId_debeRetornarListaVacia_cuandoNoExiste() {
        List<Orden> resultado = ordenRepository.findByClienteId(999L);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByArtistaId_debeRetornarListaVacia_cuandoNoExiste() {
        List<Orden> resultado = ordenRepository.findByArtistaId(999L);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByFechaLimiteBeforeAndEstadoNotIn_debeFuncionarCorrectamente() {
        List<OrderResponseDTO> resultado = ordenRepository.findByFechaLimiteBeforeAndEstadoNotIn(
                ZonedDateTime.now(),
                List.of(EstadoOrden.COMPLETADA, EstadoOrden.RECHAZADA)
        );
        assertThat(resultado).isEmpty();
    }
}