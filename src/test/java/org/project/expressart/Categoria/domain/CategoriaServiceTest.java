package org.project.expressart.Categoria.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.dto.CategoryRequestDTO;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoriaRepository categoryRepository;

    // 1. Usamos una instancia REAL de ModelMapper para evitar mocks nulos
    private final ModelMapper modelMapper = new ModelMapper();

    // Quitamos @InjectMocks para inicializar con seguridad quirúrjica
    private CategoryService categoryService;

    private Categoria categoria;
    private CategoryRequestDTO requestDTO;
    private CategoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // 2. Creamos la instancia del servicio pasándole el repositorio mockeado
        categoryService = new CategoryService(categoryRepository);

        // 3. Forzamos la inyección del ModelMapper real directo al atributo del servicio
        // Esto soluciona el problema aunque falte el "final" o el constructor correcto en el Service
        ReflectionTestUtils.setField(categoryService, "modelMapper", modelMapper);

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Pintura");

        requestDTO = new CategoryRequestDTO();
        requestDTO.setNombre("Pintura");

        responseDTO = new CategoryResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Pintura");
    }

    @Test
    void findAll_debeRetornarListaDeDtos() {
        Page<Categoria> page = new PageImpl<>(List.of(categoria));
        when(categoryRepository.findAllBy(any(Pageable.class))).thenReturn(page);

        List<CategoryResponseDTO> resultado = categoryService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pintura", resultado.get(0).getNombre());
        verify(categoryRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_cuandoExiste_debeRetornarDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoryResponseDTO resultado = categoryService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Pintura", resultado.getNombre());
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarExcepcion() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundEXception.class, () -> categoryService.findById(1L));
    }

    @Test
    void findByNombre_cuandoExiste_debeRetornarDto() {
        when(categoryRepository.findByNombre("Pintura")).thenReturn(Optional.of(categoria));

        CategoryResponseDTO resultado = categoryService.findByNombre("Pintura");

        assertNotNull(resultado);
        assertEquals("Pintura", resultado.getNombre());
    }

    @Test
    void create_cuandoNombreYaExiste_debeLanzarExcepcion() {
        when(categoryRepository.existsByNombre("Pintura")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.create(requestDTO));
        verify(categoryRepository, never()).save(any(Categoria.class));
    }

    @Test
    void create_cuandoDatosValidos_debeGuardarYRetornarDto() {
        when(categoryRepository.existsByNombre("Pintura")).thenReturn(false);
        when(categoryRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoryResponseDTO resultado = categoryService.create(requestDTO);

        assertNotNull(resultado);
        assertEquals("Pintura", resultado.getNombre());
        verify(categoryRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void update_cuandoExiste_debeModificarYGuardar() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoryRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoryResponseDTO resultado = categoryService.update(1L, requestDTO);

        assertNotNull(resultado);
        assertEquals("Pintura", resultado.getNombre());
        verify(categoryRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void delete_cuandoExiste_debeEliminar() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarExcepcion() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> categoryService.delete(1L));
        verify(categoryRepository, never()).deleteById(anyLong());
    }
}