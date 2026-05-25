package org.project.expressart.Tags.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TagsServiceTest {

    @Mock
    private TagsRepository tagsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TagsService tagsService;

    private Tags tagEntity;
    private TagsRequestDTO requestDTO;
    private TagsResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        tagEntity = new Tags();
        tagEntity.setId(1L);
        tagEntity.setNombre("Acuarela");

        requestDTO = new TagsRequestDTO();
        requestDTO.setNombre("Acuarela");

        responseDTO = new TagsResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Acuarela");
    }

    @Test
    void findAll_debeRetornarListaDeTags() {
        // 1. Creamos la respuesta simulada
        TagsResponseDTO responseDTO = new TagsResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Oleo");

        when(tagsRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<TagsResponseDTO> resultado = tagsService.findAll();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Oleo");


        verify(tagsRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarTag_cuandoExiste() {
        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tagEntity));
        when(modelMapper.map(any(Tags.class), eq(TagsResponseDTO.class))).thenReturn(responseDTO);

        TagsResponseDTO resultado = tagsService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Acuarela");
    }

    @Test
    void create_debeGuardarYRetornarTag() {
        when(tagsRepository.save(any(Tags.class))).thenReturn(tagEntity);
        when(modelMapper.map(any(TagsRequestDTO.class), eq(Tags.class))).thenReturn(tagEntity);
        when(modelMapper.map(any(Tags.class), eq(TagsResponseDTO.class))).thenReturn(responseDTO);

        TagsResponseDTO resultado = tagsService.create(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void delete_debeEliminarCorrectamente() {
        when(tagsRepository.existsById(1L)).thenReturn(true);
        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tagEntity));
        doNothing().when(tagsRepository).deleteById(1L);

        tagsService.delete(1L);

        verify(tagsRepository, times(1)).deleteById(1L);
    }
}