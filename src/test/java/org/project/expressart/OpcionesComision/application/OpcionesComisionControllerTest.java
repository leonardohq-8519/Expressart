package org.project.expressart.OpcionesComision.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.OpcionesComision.domain.CommissionOptionsService;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OpcionesComisionController.class)
@WithMockUser
class OpcionesComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommissionOptionsService commissionOptionsService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CommissionOptionsResponseDTO responseDTO;
    private CommissionOptionsRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CommissionOptionsResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Premium Pack");

        requestDTO = new CommissionOptionsRequestDTO();
        requestDTO.setComisionId(1L);
        requestDTO.setNombre("Premium Pack");
        requestDTO.setPrecio(BigDecimal.valueOf(250.00));
        requestDTO.setTiempoEntrega(5);
        requestDTO.setNumeroRevisiones(3);
        requestDTO.setIncluyeArchivoFuente(true);
        requestDTO.setEstaActiva(true);
    }

    @Test
    void getAll_debeRetornar200YLista() throws Exception {
        when(commissionOptionsService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commission-options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Premium Pack"));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(commissionOptionsService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/commission-options/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByComision_debeRetornarListaDeOpciones() throws Exception {
        when(commissionOptionsService.findByComisionId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commission-options/commission/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(commissionOptionsService.create(any(CommissionOptionsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/commission-options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_debeRetornar200() throws Exception {
        when(commissionOptionsService.update(eq(1L), any(CommissionOptionsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/commission-options/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(commissionOptionsService).delete(1L);

        mockMvc.perform(delete("/commission-options/1"))
                .andExpect(status().isNoContent());
    }
}