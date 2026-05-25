package org.project.expressart.PerfilArtista.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PerfilArtistaRepositoryTest {

    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;

    @Test
    void findByUsuarioId_debeRetornarVacio_cuandoNoExiste() {
        Optional<PerfilArtista> resultado = perfilArtistaRepository.findByUsuarioId(999L);
        assertThat(resultado).isEmpty();
    }
}