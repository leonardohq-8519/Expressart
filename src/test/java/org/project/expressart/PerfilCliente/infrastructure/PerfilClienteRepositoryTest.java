package org.project.expressart.PerfilCliente.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PerfilClienteRepositoryTest {

    @Autowired
    private PerfilClienteRepository perfilClienteRepository;

    @Test
    void findByUsuarioId_debeRetornarVacio_cuandoNoExiste() {
        Optional<PerfilCliente> resultado = perfilClienteRepository.findByUsuarioId(999L);
        assertThat(resultado).isEmpty();
    }
}