package org.project.expressart.ArchivoPost.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostUpdateDTO;
import org.project.expressart.ArchivoPost.domain.ArchivePostService;
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
        controllers = ArchivoPostController.class,
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
class ArchivoPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ArchivePostService archivoPostService;

    private ArchivoPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ArchivoPostResponseDTO(1L, "http://example.com/archivo.zip", 1);
    }

    @Test
    @DisplayName("Debería retornar 200 con lista de archivos por post")
    void shouldReturn200WithArchivoListWhenGetByPost() throws Exception {
        when(archivoPostService.getByPost(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/post-files/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].url").value("http://example.com/archivo.zip"));
    }

    @Test
    @DisplayName("Debería retornar 200 con el archivo cuando existe por ID")
    void shouldReturn200WhenArchivoFoundById() throws Exception {
        when(archivoPostService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/post-files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear un archivo de post válido")
    void shouldReturn201WhenArchivoCreatedSuccessfully() throws Exception {
        ArchivoPostCreateDTO request = new ArchivoPostCreateDTO(1L, "http://example.com/nuevo.zip", 1);

        when(archivoPostService.create(any(ArchivoPostCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/post-files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 al actualizar un archivo de post")
    void shouldReturn200WhenArchivoUpdatedSuccessfully() throws Exception {
        ArchivoPostUpdateDTO request = new ArchivoPostUpdateDTO("http://example.com/updated.zip", 2);

        when(archivoPostService.update(eq(1L), any(ArchivoPostUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/post-files/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar un archivo de post")
    void shouldReturn204WhenArchivoDeletedSuccessfully() throws Exception {
        doNothing().when(archivoPostService).delete(1L);

        mockMvc.perform(delete("/api/post-files/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar archivos por post")
    void shouldReturn204WhenArchivosDeletedByPost() throws Exception {
        doNothing().when(archivoPostService).deleteByPost(1L);

        mockMvc.perform(delete("/api/post-files/post/1"))
                .andExpect(status().isNoContent());
    }
}