package org.project.expressart.Post.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Post.domain.PostService;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
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

@WebMvcTest(controllers = PostController.class)
@WithMockUser
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

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
    void shouldReturn404WhenGetByIdAndPostNotFound() throws Exception {
        when(postService.findById(99L)).thenThrow(new ResourceNotFoundException("Post not found"));

        mockMvc.perform(get("/posts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPostsByPortafolioWhenGetByPortafolioId() throws Exception {
        when(postService.findByPortafolioId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/posts/portafolio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnPublicPostsWhenGetPublicosByPortafolio() throws Exception {
        when(postService.findByPortafolioIdAndEsPublico(1L, true)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/posts/portafolio/1/publicos"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(postService).delete(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }
}