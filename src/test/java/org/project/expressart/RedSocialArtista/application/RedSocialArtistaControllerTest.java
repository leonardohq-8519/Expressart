package org.project.expressart.RedSocialArtista.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.RedSocialArtista.domain.ArtistSocialMediaService;
import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = RedSocialArtistaController.class,
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
class RedSocialArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ArtistSocialMediaService artistSocialMediaService;

    private RedSocialArtista redSocialArtista;

    @BeforeEach
    void setUp() {
        PerfilArtista perfilArtista = new PerfilArtista();
        perfilArtista.setId(1L);

        redSocialArtista = new RedSocialArtista();
        redSocialArtista.setId(1L);
        redSocialArtista.setPerfilArtista(perfilArtista);
        redSocialArtista.setPlataforma("instagram");
        redSocialArtista.setUrl("https://instagram.com/artista");
    }

    @Test
    @DisplayName("Debería retornar 200 con lista de redes sociales por artista")
    void shouldReturn200WithRedesSocialesWhenGetByArtista() throws Exception {
        when(artistSocialMediaService.findByArtistaId(1L)).thenReturn(List.of(redSocialArtista));

        mockMvc.perform(get("/api/red-social-artista/artista/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].plataforma").value("instagram"));
    }

    @Test
    @DisplayName("Debería retornar 200 con lista vacía cuando el artista no tiene redes sociales")
    void shouldReturn200WithEmptyListWhenArtistaHasNoRedesSociales() throws Exception {
        when(artistSocialMediaService.findByArtistaId(99L)).thenReturn(List.of());

        mockMvc.perform(get("/api/red-social-artista/artista/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Debería retornar 200 al crear una red social válida")
    void shouldReturn200WhenRedSocialCreatedSuccessfully() throws Exception {
        when(artistSocialMediaService.create(any(RedSocialArtista.class))).thenReturn(redSocialArtista);

        mockMvc.perform(post("/api/red-social-artista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(redSocialArtista)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.plataforma").value("instagram"));
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar una red social existente")
    void shouldReturn204WhenRedSocialDeletedSuccessfully() throws Exception {
        doNothing().when(artistSocialMediaService).delete(1L);

        mockMvc.perform(delete("/api/red-social-artista/1"))
                .andExpect(status().isNoContent());
    }
}