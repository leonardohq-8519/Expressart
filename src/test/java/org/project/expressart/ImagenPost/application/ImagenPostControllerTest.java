package org.project.expressart.ImagenPost.application;

import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenPost.application.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.domain.PostPictureService;
import org.project.expressart.config.JwtService;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImagenPostController.class)
@AutoConfigureMockMvc(addFilters = false)
class ImagenPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private PostPictureService postPictureService;

    @Test
    void getImagenesByPost_DebeRetornarListaYStatus200() throws Exception {
        ImagenPostResponseDTO imgDto = new ImagenPostResponseDTO();
        imgDto.setId(10L);
        imgDto.setUrl("/storage/posts/1/foto.jpg");
        imgDto.setOrden(0);

        when(postPictureService.getByPostId(anyLong())).thenReturn(List.of(imgDto));

        mockMvc.perform(get("/api/imagen-posts/post/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteImagen_DebeRetornarStatus204() throws Exception {
        doNothing().when(postPictureService).deleteByPost(anyLong());

        mockMvc.perform(delete("/api/imagen-posts/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}