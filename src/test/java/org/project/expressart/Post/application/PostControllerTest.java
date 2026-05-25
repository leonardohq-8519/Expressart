package org.project.expressart.Post.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Post.domain.PostService;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
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
        controllers = PostController.class,
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
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostResponseDTO responseDTO;
    private PostRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Mi Obra");
        responseDTO.setDescripcion("Una gran pintura");
        responseDTO.setEsPublico(true);

        requestDTO = new PostRequestDTO();
        requestDTO.setPortafolioId(1L);
        requestDTO.setTitulo("Mi Obra");
        requestDTO.setDescripcion("Una gran pintura");
        requestDTO.setEsPublico(true);
        requestDTO.setCategoriaIds(List.of(1L));
        requestDTO.setTagIds(List.of(1L));
    }

    @Test
    void getAll_debeRetornar200YLista() throws Exception {
        when(postService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Mi Obra"));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(postService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201_cuandoSeCrea() throws Exception {
        when(postService.create(any(PostRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_debeRetornar200_cuandoSeActualiza() throws Exception {
        when(postService.update(eq(1L), any(PostRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(postService).delete(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }
}