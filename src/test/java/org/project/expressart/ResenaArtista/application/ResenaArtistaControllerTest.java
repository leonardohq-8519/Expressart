package org.project.expressart.ResenaArtista.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.ResenaArtista.domain.ArtistReviewService;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ResenaArtistaController.class)
@WithMockUser
class ResenaArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ArtistReviewService artistReviewService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ArtistReviewResponseDTO responseDTO;
    private ArtistReviewRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ArtistReviewResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPuntuacion((short) 5);
        responseDTO.setComentario("Excelente artista");

        requestDTO = new ArtistReviewRequestDTO();
        requestDTO.setOrdenId(1L);
        requestDTO.setClienteId(2L);
        requestDTO.setArtistaId(3L);
        requestDTO.setPuntuacion((short) 5);
        requestDTO.setComentario("Excelente artista");
    }

    @Test
    void shouldReturnAllReviewsWhenGetAll() throws Exception {
        when(artistReviewService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/artist-reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnReviewWhenGetByIdAndExists() throws Exception {
        when(artistReviewService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/artist-reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndReviewNotFound() throws Exception {
        when(artistReviewService.findById(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/artist-reviews/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnReviewsByArtistaWhenGetByArtista() throws Exception {
        when(artistReviewService.findByArtistaId(3L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/artist-reviews/artist/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        when(artistReviewService.create(any(ArtistReviewRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/artist-reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(artistReviewService).delete(1L);

        mockMvc.perform(delete("/artist-reviews/1"))
                .andExpect(status().isNoContent());
    }
}
