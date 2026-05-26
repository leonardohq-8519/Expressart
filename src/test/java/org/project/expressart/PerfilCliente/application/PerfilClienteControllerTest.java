package org.project.expressart.PerfilCliente.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilCliente.domain.ClientProfileService;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PerfilClienteController.class)
@WithMockUser
class PerfilClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientProfileService clientProfileService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private ClientProfileResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ClientProfileResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTotalResenas(10);
    }

    @Test
    void getAll_debeRetornar200() throws Exception {
        when(clientProfileService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/client-profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(clientProfileService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/client-profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByUsuario_debeRetornar200() throws Exception {
        when(clientProfileService.findByUsuarioId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/client-profiles/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(clientProfileService.create(1L)).thenReturn(responseDTO);

        mockMvc.perform(post("/client-profiles/user/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(clientProfileService).delete(1L);

        mockMvc.perform(delete("/client-profiles/1"))
                .andExpect(status().isNoContent());
    }
}