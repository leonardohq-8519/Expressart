package org.project.expressart.ImagenComision.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ImagenComisionController.class,
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
class ImagenComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debería retornar 200 con lista vacía de imágenes para una comisión")
    void shouldReturn200WithEmptyListWhenGetImagenesByComision() throws Exception {
        mockMvc.perform(get("/api/imagen-comisiones/comision/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar una imagen de comisión")
    void shouldReturn204WhenImagenComisionDeleted() throws Exception {
        mockMvc.perform(delete("/api/imagen-comisiones/1"))
                .andExpect(status().isNoContent());
    }
}