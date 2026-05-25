package org.project.expressart.Comision.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.Tags.domain.Tags;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    private final ModelMapper modelMapper = new ModelMapper();
    private CommissionService commissionService;

    private Comision comision;
    private CommissionRequestDTO requestDTO;
    private CommissionResponseDTO responseDTO;
    private PerfilArtista perfilArtista;

    @BeforeEach
    void setUp() {
        commissionService = new CommissionService(commissionRepository, artistProfileRepository, categoryRepository, tagsRepository);
        ReflectionTestUtils.setField(commissionService, "modelMapper", modelMapper);

        perfilArtista = new PerfilArtista();
        perfilArtista.setId(1L);

        comision = new Comision();
        comision.setId(1L);
        comision.setTitulo("Ilustración de personaje");
        comision.setDescripcion("Dibujo de personaje original");
        comision.setPerfilArtista(perfilArtista);
        comision.setEstaActiva(true);

        requestDTO = new CommissionRequestDTO();
        requestDTO.setPerfilArtistaId(1L);
        requestDTO.setTitulo("Ilustración de personaje");
        requestDTO.setDescripcion("Dibujo de personaje original");
        requestDTO.setEstaActiva(true);
        requestDTO.setCategoriaIds(List.of(1L));
        requestDTO.setTagIds(List.of(1L));

        responseDTO = new CommissionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Ilustración de personaje");
    }

    @Test
    @DisplayName("Debería retornar lista de comisiones al llamar findAll")
    void shouldReturnCommissionListWhenFindAll() {
        when(commissionRepository.findAllBy(any())).thenReturn(List.of(responseDTO));

        List<CommissionResponseDTO> result = commissionService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commissionRepository, times(1)).findAllBy(any());
    }

    @Test
    @DisplayName("Debería retornar comisión cuando existe el ID")
    void shouldReturnCommissionWhenFindByIdExists() throws ResourceNotFoundException {
        when(commissionRepository.findById(1L)).thenReturn(Optional.of(comision));

        CommissionResponseDTO result = commissionService.findById(1L);

        assertNotNull(result);
        verify(commissionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la comisión no existe por ID")
    void shouldThrowExceptionWhenCommissionNotFoundById() {
        when(commissionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commissionService.findById(99L));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no hay comisiones para la categoría")
    void shouldThrowExceptionWhenNoCommissionsFoundByCategoriaId() {
        when(commissionRepository.findByCategoriaId(99L)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> commissionService.findByCategoriaId(99L));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no hay comisiones para el artista")
    void shouldThrowExceptionWhenNoCommissionsFoundByArtistaId() {
        when(commissionRepository.findByPerfilArtistaId(99L)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> commissionService.findByPerfilArtistaId(99L));
    }

    @Test
    @DisplayName("Debería crear comisión cuando los datos son válidos")
    void shouldCreateCommissionWhenValidData() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Arte Digital");

        Tags tag = new Tags();
        tag.setId(1L);
        tag.setNombre("Digital");

        when(artistProfileRepository.findById(1L)).thenReturn(Optional.of(perfilArtista));
        when(categoryRepository.findAllById(List.of(1L))).thenReturn(List.of(categoria));
        when(tagsRepository.findAllById(List.of(1L))).thenReturn(List.of(tag));
        when(commissionRepository.save(any(Comision.class))).thenReturn(comision);

        CommissionResponseDTO result = commissionService.create(requestDTO);

        assertNotNull(result);
        verify(commissionRepository, times(1)).save(any(Comision.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al crear comisión cuando el artista no existe")
    void shouldThrowExceptionWhenCreatingCommissionWithNonExistentArtist() {
        when(artistProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commissionService.create(requestDTO));
        verify(commissionRepository, never()).save(any(Comision.class));
    }

    @Test
    @DisplayName("Debería eliminar comisión cuando existe el ID")
    void shouldDeleteCommissionWhenExists() {
        when(commissionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(commissionRepository).deleteById(1L);

        commissionService.delete(1L);

        verify(commissionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción al eliminar comisión cuando no existe")
    void shouldThrowExceptionWhenDeletingNonExistentCommission() {
        when(commissionRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commissionService.delete(99L));
        verify(commissionRepository, never()).deleteById(anyLong());
    }
}