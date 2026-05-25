package org.project.expressart.RedSocialArtista.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistSocialMediaServiceTest {

    @Mock
    private RedSocialArtistaRepository artistSocialMediaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArtistSocialMediaService artistSocialMediaService;

    private RedSocialArtista redSocialArtista;
    private PerfilArtista perfilArtista;

    @BeforeEach
    void setUp() {
        perfilArtista = new PerfilArtista();
        perfilArtista.setId(1L);

        redSocialArtista = new RedSocialArtista();
        redSocialArtista.setId(1L);
        redSocialArtista.setPerfilArtista(perfilArtista);
        redSocialArtista.setPlataforma("instagram");
        redSocialArtista.setUrl("https://instagram.com/artista");
    }

    @Test
    @DisplayName("Debería retornar lista de redes sociales cuando el artista tiene redes")
    void shouldReturnRedesSocialesWhenArtistaHasLinks() {
        when(artistSocialMediaRepository.findByPerfilArtistaId(1L)).thenReturn(List.of(redSocialArtista));

        List<RedSocialArtista> result = artistSocialMediaService.findByArtistaId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("instagram", result.get(0).getPlataforma());
        verify(artistSocialMediaRepository, times(1)).findByPerfilArtistaId(1L);
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando el artista no tiene redes sociales")
    void shouldReturnEmptyListWhenArtistaHasNoLinks() {
        when(artistSocialMediaRepository.findByPerfilArtistaId(99L)).thenReturn(List.of());

        List<RedSocialArtista> result = artistSocialMediaService.findByArtistaId(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debería guardar y retornar red social cuando los datos son válidos")
    void shouldCreateRedSocialWhenValidData() {
        when(artistSocialMediaRepository.save(any(RedSocialArtista.class))).thenReturn(redSocialArtista);

        RedSocialArtista result = artistSocialMediaService.create(redSocialArtista);

        assertNotNull(result);
        assertEquals("instagram", result.getPlataforma());
        assertEquals("https://instagram.com/artista", result.getUrl());
        verify(artistSocialMediaRepository, times(1)).save(any(RedSocialArtista.class));
    }

    @Test
    @DisplayName("Debería eliminar red social cuando existe el ID")
    void shouldDeleteRedSocialWhenExists() {
        when(artistSocialMediaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(artistSocialMediaRepository).deleteById(1L);

        assertDoesNotThrow(() -> artistSocialMediaService.delete(1L));
        verify(artistSocialMediaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción al eliminar red social cuando no existe")
    void shouldThrowExceptionWhenDeletingNonExistentRedSocial() {
        when(artistSocialMediaRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> artistSocialMediaService.delete(99L));
        verify(artistSocialMediaRepository, never()).deleteById(anyLong());
    }
}