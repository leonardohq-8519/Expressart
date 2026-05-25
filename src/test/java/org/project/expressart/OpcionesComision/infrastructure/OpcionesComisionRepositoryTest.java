package org.project.expressart.OpcionesComision.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(excludeAutoConfiguration = SecurityAutoConfiguration.class) // Evita problemas con Spring Security en tests de JPA
@ActiveProfiles("local")
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
        List<OpcionesComision> resultado = opcionesComisionRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByComisionId_debeRetornarListaVacia_siIdNoExiste() {
        List<OpcionesComision> resultado = opcionesComisionRepository.findByComisionId(999L);
        assertThat(resultado).isEmpty();
    }
}