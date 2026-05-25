package org.project.expressart.ResenaArtista.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.expressart.RedSocialArtista.application.RedSocialArtistaController;
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
class ResenaArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistSocialMediaService artistSocialMediaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByArtista_debeRetornar200() throws Exception {
        when(artistSocialMediaService.findByArtistaId(1L)).thenReturn(List.of(new RedSocialArtista()));

        mockMvc.perform(get("/api/red-social-artista/artista/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create_debeRetornar200_cuandoEsValido() throws Exception {
        RedSocialArtista mockRed = new RedSocialArtista();
        mockRed.setPlataforma("Instagram");
        mockRed.setUrl("https://instagram.com/user");

        when(artistSocialMediaService.create(any(RedSocialArtista.class))).thenReturn(mockRed);

        mockMvc.perform(post("/api/red-social-artista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRed)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(artistSocialMediaService).delete(1L);

        mockMvc.perform(delete("/api/red-social-artista/1"))
                .andExpect(status().isNoContent());
    }
}