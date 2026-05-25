package org.project.expressart.PerfilArtista.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilArtista.domain.ArtistProfileService;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
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
        controllers = PerfilArtistaController.class,
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
class PerfilArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistProfileService artistProfileService;

    @Autowired
    private ObjectMapper objectMapper;

    private ArtistProfileResponseDTO responseDTO;
    private ArtistProfileRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ArtistProfileResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setComsDisponibles(true);

        requestDTO = new ArtistProfileRequestDTO();
        requestDTO.setComsDisponibles(true);
        requestDTO.setTiempoEntregaPromedio(5);
    }

    @Test
    void getAll_debeRetornar200() throws Exception {
        when(artistProfileService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/artist-profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(artistProfileService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/artist-profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByUsuario_debeRetornar200() throws Exception {
        when(artistProfileService.findByUsuarioId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/artist-profiles/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(artistProfileService.create(eq(1L), any(ArtistProfileRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/artist-profiles/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_debeRetornar200() throws Exception {
        when(artistProfileService.update(eq(1L), any(ArtistProfileRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/artist-profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(artistProfileService).delete(1L);

        mockMvc.perform(delete("/artist-profiles/1"))
                .andExpect(status().isNoContent());
    }
}