package org.project.expressart.OpcionesComision.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OpcionesComisionRepositoryTest {

    @Autowired
    private OpcionesComisionRepository opcionesComisionRepository;

    @Test
    void findById_debeRetornarVacio_siElIdNoExisteEnBaseDeDatos() {
        Optional<OpcionesComision> resultado = opcionesComisionRepository.findById(999L);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findAllBy_debeRetornarListaVacia_cuandoNoHayRegistros() {
        List<CommissionOptionsResponseDTO> resultado = opcionesComisionRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByComisionId_debeRetornarListaVacia_siIdNoExiste() {
        List<OpcionesComision> resultado = opcionesComisionRepository.findByComisionId(999L);
        assertThat(resultado).isEmpty();
    }
}