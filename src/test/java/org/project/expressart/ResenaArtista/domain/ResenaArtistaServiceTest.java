package org.project.expressart.ResenaArtista.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.ResenaArtista.infrastructure.ResenaArtistaRepository;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaArtistaServiceTest {

    @Mock
    private ResenaArtistaRepository artistReviewRepository;

    @Mock
    private OrdenRepository orderRepository;

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArtistReviewService artistReviewService;

    private ResenaArtista resena;
    private ArtistReviewResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(artistReviewService, "modelMapper", modelMapper);
        resena = new ResenaArtista();
        resena.setId(1L);

        responseDTO = new ArtistReviewResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPuntuacion((short) 5);
    }

    @Test
    void shouldReturnReviewWhenFindByIdAndExists() {
        when(artistReviewRepository.findById(1L)).thenReturn(Optional.of(resena));
        when(modelMapper.map(resena, ArtistReviewResponseDTO.class)).thenReturn(responseDTO);

        ArtistReviewResponseDTO result = artistReviewService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenFindByIdAndNotExists() {
        when(artistReviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artistReviewService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnReviewsWhenFindByArtistaIdAndExist() {
        when(artistReviewRepository.findByArtistaId(1L)).thenReturn(List.of(resena));
        when(modelMapper.map(resena, ArtistReviewResponseDTO.class)).thenReturn(responseDTO);

        List<ArtistReviewResponseDTO> result = artistReviewService.findByArtistaId(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldThrowResourceNotFoundWhenFindByArtistaIdAndNoneExist() {
        when(artistReviewRepository.findByArtistaId(99L)).thenReturn(List.of());

        assertThatThrownBy(() -> artistReviewService.findByArtistaId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldDeleteReviewWhenExists() {
        when(artistReviewRepository.existsById(1L)).thenReturn(true);

        artistReviewService.delete(1L);

        verify(artistReviewRepository).deleteById(1L);
    }
}
