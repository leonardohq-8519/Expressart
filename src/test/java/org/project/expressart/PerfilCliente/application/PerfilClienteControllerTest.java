package org.project.expressart.PerfilCliente.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.PerfilCliente.domain.ClientProfileService;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = PerfilClienteController.class,
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
class PerfilClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientProfileService clientProfileService;

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