package org.project.expressart.Tags.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Tags.domain.TagsService;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
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

@WebMvcTest(controllers = TagsController.class)
@WithMockUser
class TagsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private TagsService tagsService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private TagsRequestDTO requestDTO;
    private TagsResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new TagsRequestDTO();
        requestDTO.setNombre("Oleo");

        responseDTO = new TagsResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Oleo");
    }

    @Test
    void getAll_debeRetornar200_conListaDeTags() throws Exception {
        when(tagsService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Oleo"));
    }

    @Test
    void getById_debeRetornar200_cuandoExisteTag() throws Exception {
        when(tagsService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Oleo"));
    }

    @Test
    void create_debeRetornar201_cuandoDatosValidos() throws Exception {
        when(tagsService.create(any(TagsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Oleo"));
    }

    @Test
    void shouldReturnTagWhenGetByNameAndExists() throws Exception {
        when(tagsService.findByNombre("Oleo")).thenReturn(responseDTO);

        mockMvc.perform(get("/tags/name/Oleo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Oleo"));
    }

    @Test
    void shouldReturn404WhenGetByIdAndTagNotFound() throws Exception {
        when(tagsService.findById(99L)).thenThrow(new ResourceNotFoundException("Tag not found"));

        mockMvc.perform(get("/tags/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        when(tagsService.update(eq(1L), any(TagsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(tagsService).delete(1L);

        mockMvc.perform(delete("/tags/1"))
                .andExpect(status().isNoContent());
    }
}