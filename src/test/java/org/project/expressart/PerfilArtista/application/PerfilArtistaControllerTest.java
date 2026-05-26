package org.project.expressart.PerfilArtista.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilArtista.domain.ArtistProfileService;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PerfilArtistaController.class)
@WithMockUser
class PerfilArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ArtistProfileService artistProfileService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

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
    void shouldReturn404WhenGetByIdAndProfileNotFound() throws Exception {
        when(artistProfileService.findById(99L)).thenThrow(new ResourceNotFoundException("Profile not found"));

        mockMvc.perform(get("/artist-profiles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(artistProfileService).delete(1L);

        mockMvc.perform(delete("/artist-profiles/1"))
                .andExpect(status().isNoContent());
    }
}