package org.project.expressart.ResenaArtista.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.RedSocialArtista.domain.ArtistSocialMediaService;
import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaArtistaServiceTest {

    @Mock
    private RedSocialArtistaRepository artistSocialMediaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArtistSocialMediaService artistSocialMediaService;

    @Test
    void findByArtistaId_debeRetornarLista() {
        RedSocialArtista red = new RedSocialArtista();
        when(artistSocialMediaRepository.findByPerfilArtistaId(1L)).thenReturn(List.of(red));

        List<RedSocialArtista> resultado = artistSocialMediaService.findByArtistaId(1L);

        assertThat(resultado).isNotEmpty();
        verify(artistSocialMediaRepository, times(1)).findByPerfilArtistaId(1L);
    }

    @Test
    void create_debeGuardarCorrectamente() {
        RedSocialArtista red = new RedSocialArtista();
        when(artistSocialMediaRepository.save(any(RedSocialArtista.class))).thenReturn(red);

        RedSocialArtista resultado = artistSocialMediaService.create(red);

        assertThat(resultado).isNotNull();
        verify(artistSocialMediaRepository, times(1)).save(red);
    }
}