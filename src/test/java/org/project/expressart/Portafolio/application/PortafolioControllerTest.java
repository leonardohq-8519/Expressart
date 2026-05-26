package org.project.expressart.Portafolio.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Portafolio.domain.PortafolioService;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PortafolioController.class)
@WithMockUser
class PortafolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PortafolioService portafolioService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PortafolioResponseDTO responseDTO;
    private PortafolioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PortafolioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Portafolio Óleo");
        responseDTO.setDescripcion("Pinturas al óleo");

        requestDTO = new PortafolioRequestDTO();
        requestDTO.setTitulo("Portafolio Óleo");
        requestDTO.setDescripcion("Pinturas al óleo");
        requestDTO.setPortada_url("https://url-portada.com");
    }

    @Test
    void getAll_debeRetornar200() throws Exception {
        when(portafolioService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/portfolios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Portafolio Óleo"));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(portafolioService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/portfolios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(portafolioService.create(any(PortafolioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_debeRetornar200() throws Exception {
        when(portafolioService.update(eq(1L), any(PortafolioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/portfolios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(portafolioService).delete(1L);

        mockMvc.perform(delete("/portfolios/1"))
                .andExpect(status().isNoContent());
    }
}