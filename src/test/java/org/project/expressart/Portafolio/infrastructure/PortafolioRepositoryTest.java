package org.project.expressart.Portafolio.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PortafolioRepositoryTest {

    @Autowired
    private PortafolioRepository portafolioRepository;

    @Test
    void findByPerfilArtistaId_debeRetornarVacio_cuandoNoExiste() {
        List<Portafolio> resultado = portafolioRepository.findByPerfilArtistaId(999L);
        assertThat(resultado).isEmpty();
    }
}