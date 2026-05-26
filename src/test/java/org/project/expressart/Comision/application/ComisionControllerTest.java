package org.project.expressart.Comision.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Comision.domain.CommissionService;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
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

@WebMvcTest(controllers = ComisionController.class)
@WithMockUser
class ComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CommissionService commissionService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private CommissionResponseDTO responseDTO;
    private CommissionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CommissionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Retratos al Óleo");
        responseDTO.setEstaActiva(true);

        requestDTO = new CommissionRequestDTO();
        requestDTO.setPerfilArtistaId(1L);
        requestDTO.setTitulo("Retratos al Óleo");
        requestDTO.setDescripcion("Retratos personalizados");
        requestDTO.setEstaActiva(true);
        requestDTO.setCategoriaIds(List.of(1L));
        requestDTO.setTagIds(List.of(1L));
    }

    @Test
    void shouldReturnAllCommissionsWhenGetAll() throws Exception {
        when(commissionService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Retratos al Óleo"));
    }

    @Test
    void shouldReturnCommissionWhenGetByIdAndExists() throws Exception {
        when(commissionService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/commissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenGetByIdAndCommissionNotFound() throws Exception {
        when(commissionService.findById(99L)).thenThrow(new ResourceNotFoundException("Commission not found"));

        mockMvc.perform(get("/commissions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCommissionsByArtistProfileWhenGetByPerfilArtista() throws Exception {
        when(commissionService.findByPerfilArtistaId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions/artist-profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnActiveCommissionsWhenGetActiveByPerfilArtista() throws Exception {
        when(commissionService.findByPerfilArtistaIdAndEstaActiva(1L, true)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/commissions/artist-profile/1/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estaActiva").value(true));
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        when(commissionService.create(any(CommissionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/commissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        when(commissionService.update(eq(1L), any(CommissionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/commissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(commissionService).delete(1L);

        mockMvc.perform(delete("/commissions/1"))
                .andExpect(status().isNoContent());
    }
}
