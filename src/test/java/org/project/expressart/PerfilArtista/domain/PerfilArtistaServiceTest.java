package org.project.expressart.PerfilArtista.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilArtistaServiceTest {

    @Mock
    private PerfilArtistaRepository artistProfileRepository;

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private CategoriaRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArtistProfileService artistProfileService;

    private PerfilArtista perfilArtista;
    private ArtistProfileResponseDTO responseDTO;
    private ArtistProfileRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(artistProfileService, "modelMapper", modelMapper);
        perfilArtista = new PerfilArtista();
        perfilArtista.setId(1L);

        responseDTO = new ArtistProfileResponseDTO();
        responseDTO.setId(1L);

        requestDTO = new ArtistProfileRequestDTO();
        requestDTO.setComsDisponibles(true);
        requestDTO.setTiempoEntregaPromedio(3);
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(artistProfileRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<ArtistProfileResponseDTO> resultado = artistProfileService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(artistProfileRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarPerfil_cuandoExiste() throws Exception {
        when(artistProfileRepository.findById(1L)).thenReturn(Optional.of(perfilArtista));
        when(modelMapper.map(perfilArtista, ArtistProfileResponseDTO.class)).thenReturn(responseDTO);

        ArtistProfileResponseDTO resultado = artistProfileService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarPerfilExitosamente() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(artistProfileRepository.save(any(PerfilArtista.class))).thenReturn(perfilArtista);
        when(modelMapper.map(any(PerfilArtista.class), eq(ArtistProfileResponseDTO.class))).thenReturn(responseDTO);

        ArtistProfileResponseDTO resultado = artistProfileService.create(1L, requestDTO);

        assertThat(resultado).isNotNull();
        verify(artistProfileRepository, times(1)).save(any(PerfilArtista.class));
    }
}