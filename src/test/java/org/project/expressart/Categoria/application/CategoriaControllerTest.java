package org.project.expressart.Categoria.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Categoria.domain.CategoryService;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CategoriaController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = org.project.expressart.config.JwtAuthFilter.class
        )
)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    private CategoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CategoryResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Pintura");
        responseDTO.setDescripcion("Arte de pintura");
    }

    @Test
    @DisplayName("Debería retornar 200 y lista de categorías al obtener todas")
    void shouldReturn200WithCategoriaListWhenFindAll() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Pintura"));
    }

    @Test
    @DisplayName("Debería retornar 200 con la categoría cuando existe por ID")
    void shouldReturn200WhenCategoriaFoundById() throws Exception {
        when(categoryService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pintura"));
    }

    @Test
    @DisplayName("Debería retornar 200 con la categoría cuando se busca por nombre")
    void shouldReturn200WhenCategoriaFoundByNombre() throws Exception {
        when(categoryService.findByNombre("Pintura")).thenReturn(responseDTO);

        mockMvc.perform(get("/api/categorias/nombre/Pintura"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pintura"));
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay categorías")
    void shouldReturnEmptyListWhenNoCategorias() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}