package org.project.expressart.ResenaCliente.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.ResenaCliente.domain.ClientReviewService;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
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
        controllers = ResenaClienteController.class,
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
class ResenaClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientReviewService clientReviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientReviewRequestDTO requestDTO;
    private ClientReviewResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ClientReviewRequestDTO();
        requestDTO.setOrdenId(1L);
        requestDTO.setClienteId(2L);
        requestDTO.setArtistaId(3L);
        requestDTO.setPuntuacion((short) 5);
        requestDTO.setComentario("Excelente obra");

        responseDTO = new ClientReviewResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPuntuacion((short) 5);
        responseDTO.setComentario("Excelente obra");
    }

    @Test
    void getAll_debeRetornar200YLista() throws Exception {
        when(clientReviewService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/client-reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].comentario").value("Excelente obra"));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(clientReviewService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/client-reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201_cuandoEsValido() throws Exception {
        when(clientReviewService.create(any(ClientReviewRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/client-reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(clientReviewService).delete(1L);

        mockMvc.perform(delete("/client-reviews/1"))
                .andExpect(status().isNoContent());
    }
}