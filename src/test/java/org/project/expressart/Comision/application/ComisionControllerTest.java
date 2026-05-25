
package org.project.expressart.Comision.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Comision.domain.CommissionService;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ComisionController.class,
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
class ComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommissionService commissionService;

    private CommissionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CommissionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Ilustración de personaje");
        responseDTO.setEstaActiva(true);
    }

    @Test
    @DisplayName("Debería retornar 200 con lista de comisiones al obtener todas")
    void shouldReturn200WithCommissionListWhenGetAll() throws Exception {
        when(commissionService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Ilustración de personaje"));
    }

    @Test
    @DisplayName("Debería retornar 200 con la comisión cuando existe por ID")
    void shouldReturn200WhenCommissionFoundById() throws Exception {
        when(commissionService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/commissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Ilustración de personaje"));
    }

    @Test
    @DisplayName("Debería retornar 200 con comisiones por perfil de artista")
    void shouldReturn200WhenCommissionsFoundByArtistProfile() throws Exception {
        when(commissionService.findByPerfilArtistaId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions/artist-profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 con comisiones activas por artista")
    void shouldReturn200WhenActiveCommissionsFoundByArtistProfile() throws Exception {
        when(commissionService.findByPerfilArtistaIdAndEstaActiva(1L, true)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions/artist-profile/1/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estaActiva").value(true));
    }

    @Test
    @DisplayName("Debería retornar 200 con comisiones por categoría")
    void shouldReturn200WhenCommissionsFoundByCategoria() throws Exception {
        when(commissionService.findByCategoriaId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear una comisión válida")
    void shouldReturn201WhenCommissionCreatedSuccessfully() throws Exception {
        CommissionRequestDTO request = new CommissionRequestDTO();
        request.setPerfilArtistaId(1L);
        request.setTitulo("Nueva Comisión");
        request.setDescripcion("Descripción");
        request.setEstaActiva(true);
        request.setCategoriaIds(List.of(1L));
        request.setTagIds(List.of(1L));

        when(commissionService.create(any(CommissionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/commissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 al actualizar una comisión existente")
    void shouldReturn200WhenCommissionUpdatedSuccessfully() throws Exception {
        CommissionRequestDTO request = new CommissionRequestDTO();
        request.setPerfilArtistaId(1L);
        request.setTitulo("Comisión Actualizada");
        request.setCategoriaIds(List.of(1L));
        request.setTagIds(List.of(1L));

        when(commissionService.update(eq(1L), any(CommissionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/commissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar una comisión existente")
    void shouldReturn204WhenCommissionDeletedSuccessfully() throws Exception {
        doNothing().when(commissionService).delete(1L);

        mockMvc.perform(delete("/commissions/1"))
                .andExpect(status().isNoContent());
    }
}