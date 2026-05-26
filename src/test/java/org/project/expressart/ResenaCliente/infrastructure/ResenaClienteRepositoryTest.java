package org.project.expressart.ResenaCliente.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class ResenaClienteRepositoryTest {

    @Autowired
    private ResenaClienteRepository resenaClienteRepository;

    @Test
    void findByClienteId_debeRetornarListaVacia_cuandoNoExistenResenas() {
        List<ResenaCliente> resultado = resenaClienteRepository.findByClienteId(999L);
        assertThat(resultado).isEmpty();
    }
}