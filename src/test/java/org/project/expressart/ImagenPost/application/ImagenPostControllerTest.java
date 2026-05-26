package org.project.expressart.ImagenPost.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.ImagenPost.domain.PostPictureService;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostUpdateDTO;
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

@WebMvcTest(controllers = ImagenPostController.class)
@WithMockUser
class ImagenPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private PostPictureService postPictureService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ImagenPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ImagenPostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/post-img.jpg");
        responseDTO.setOrden(0);
    }

    @Test
    void shouldReturnImagesWhenGetByPostAndExists() throws Exception {
        when(postPictureService.getByPostId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/post-images/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByPostAndNotFound() throws Exception {
        when(postPictureService.getByPostId(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/post-images/post/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnImageWhenGetByIdAndExists() throws Exception {
        when(postPictureService.getById(1L, 5L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/post-images/1/images/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndImageNotFound() throws Exception {
        when(postPictureService.getById(1L, 99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/post-images/1/images/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        ImagenPostUpdateDTO updateDTO = new ImagenPostUpdateDTO("https://updated.jpg", 1);
        when(postPictureService.update(eq(1L), any(ImagenPostUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/post-images/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteByPostIsSuccessful() throws Exception {
        doNothing().when(postPictureService).deleteByPost(1L);

        mockMvc.perform(delete("/api/post-images/post/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenDeleteImageIsSuccessful() throws Exception {
        doNothing().when(postPictureService).delete(1L, 5L);

        mockMvc.perform(delete("/api/post-images/1/images/5"))
                .andExpect(status().isNoContent());
    }
}
