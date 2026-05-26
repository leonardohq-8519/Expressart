package org.project.expressart.ArchivoPost.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.ArchivoPost.domain.ArchivePostService;
import org.project.expressart.ArchivoPost.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostUpdateDTO;
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

@WebMvcTest(controllers = ArchivoPostController.class)
@WithMockUser
class ArchivoPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ArchivePostService archivePostService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ArchivoPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ArchivoPostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/file.pdf");
        responseDTO.setOrdenVisualizacion(1);
    }

    @Test
    void shouldReturnArchivesWhenGetByPostAndExists() throws Exception {
        when(archivePostService.getByPost(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/post-files/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByPostAndNotFound() throws Exception {
        when(archivePostService.getByPost(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/post-files/post/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnArchiveWhenGetByIdAndExists() throws Exception {
        when(archivePostService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/post-files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndNotFound() throws Exception {
        when(archivePostService.getById(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/post-files/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        ArchivoPostCreateDTO createDTO = new ArchivoPostCreateDTO(1L, "https://s3.amazonaws.com/file.pdf", 1);
        when(archivePostService.create(any(ArchivoPostCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/post-files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        ArchivoPostUpdateDTO updateDTO = new ArchivoPostUpdateDTO("https://updated.pdf", 2);
        when(archivePostService.update(eq(1L), any(ArchivoPostUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/post-files/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(archivePostService).delete(1L);

        mockMvc.perform(delete("/api/post-files/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenDeleteByPostIsSuccessful() throws Exception {
        doNothing().when(archivePostService).deleteByPost(1L);

        mockMvc.perform(delete("/api/post-files/post/1"))
                .andExpect(status().isNoContent());
    }
}
