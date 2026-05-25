package org.project.expressart.ResenaArtista.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class ResenaArtistaRepositoryTest {

    @Autowired
    private RedSocialArtistaRepository redSocialArtistaRepository;

    @Test
    void findByPerfilArtistaId_debeRetornarVacioSiNoExiste() {
        List<RedSocialArtista> resultado = redSocialArtistaRepository.findByPerfilArtistaId(999L);
        assertThat(resultado).isEmpty();
    }
}