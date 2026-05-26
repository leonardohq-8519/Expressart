package org.project.expressart.Comision.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComisionServiceTest {

    @Mock
    private ComisionRepository commissionRepository;

    @Mock
    private PerfilArtistaRepository artistProfileRepository;

    @Mock
    private CategoriaRepository categoryRepository;

    @Mock
    private TagsRepository tagsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommissionService commissionService;

    private Comision comision;
    private CommissionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(commissionService, "modelMapper", modelMapper);
        comision = new Comision();
        comision.setId(1L);
        comision.setTitulo("Retratos al Óleo");
        comision.setEstaActiva(true);

        responseDTO = new CommissionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Retratos al Óleo");
    }

    @Test
    void shouldReturnCommissionWhenFindByIdAndExists() {
        when(commissionRepository.findById(1L)).thenReturn(Optional.of(comision));
        when(modelMapper.map(comision, CommissionResponseDTO.class)).thenReturn(responseDTO);

        CommissionResponseDTO result = commissionService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitulo()).isEqualTo("Retratos al Óleo");
    }

    @Test
    void shouldThrowResourceNotFoundWhenFindByIdAndNotExists() {
        when(commissionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commissionService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Commission not found");
    }

    @Test
    void shouldReturnEmptyListWhenFindByPerfilArtistaIdAndNoneExist() {
        when(commissionRepository.findByPerfilArtistaId(99L)).thenReturn(List.of());

        assertThatThrownBy(() -> commissionService.findByPerfilArtistaId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnCommissionsWhenFindByPerfilArtistaIdAndExist() {
        when(commissionRepository.findByPerfilArtistaId(1L)).thenReturn(List.of(comision));
        when(modelMapper.map(comision, CommissionResponseDTO.class)).thenReturn(responseDTO);

        List<CommissionResponseDTO> result = commissionService.findByPerfilArtistaId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void shouldDeleteCommissionWhenExists() {
        when(commissionRepository.existsById(1L)).thenReturn(true);

        commissionService.delete(1L);

        verify(commissionRepository).deleteById(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenDeleteAndNotExists() {
        when(commissionRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> commissionService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
