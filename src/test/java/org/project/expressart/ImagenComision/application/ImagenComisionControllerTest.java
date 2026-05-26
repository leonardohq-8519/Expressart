package org.project.expressart.ImagenComision.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.ImagenComision.domain.CommissionPictureService;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
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

@WebMvcTest(controllers = ImagenComisionController.class)
@WithMockUser
class ImagenComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CommissionPictureService commissionPictureService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ImagenComisionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ImagenComisionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/img.jpg");
        responseDTO.setOrden(0);
    }

    @Test
    void shouldReturnImagesWhenGetByCommissionAndExists() throws Exception {
        when(commissionPictureService.getByCommission(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/commission-images/commission/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByCommissionAndNotFound() throws Exception {
        when(commissionPictureService.getByCommission(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/commission-images/commission/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnImageWhenGetByIdAndExists() throws Exception {
        when(commissionPictureService.getById(1L, 5L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/commission-images/1/images/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndImageNotFound() throws Exception {
        when(commissionPictureService.getById(1L, 99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/commission-images/1/images/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        ImagenComisionUpdateDTO updateDTO = new ImagenComisionUpdateDTO("https://updated.jpg", 1);
        when(commissionPictureService.update(eq(1L), any(ImagenComisionUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/commission-images/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteByCommissionIsSuccessful() throws Exception {
        doNothing().when(commissionPictureService).deleteByCommission(1L);

        mockMvc.perform(delete("/api/commission-images/commission/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenDeleteImageIsSuccessful() throws Exception {
        doNothing().when(commissionPictureService).delete(1L, 5L);

        mockMvc.perform(delete("/api/commission-images/1/images/5"))
                .andExpect(status().isNoContent());
    }
}
