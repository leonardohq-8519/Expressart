package org.project.expressart.Tags.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Tags.domain.TagsService;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
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
        controllers = TagsController.class,
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
class TagsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TagsService tagsService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void delete_debeRetornar204_cuandoSeElimina() throws Exception {
        doNothing().when(tagsService).delete(1L);

        mockMvc.perform(delete("/tags/1"))
                .andExpect(status().isNoContent());
    }
}