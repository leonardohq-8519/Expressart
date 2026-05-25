package org.project.expressart.CuentaOAuth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthUpdateDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthAccountService;
import org.project.expressart.CuentaOAuth.infrastructure.CuentaOAuthController;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CuentaOAuthController.class,
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
class CuentaOAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OAuthAccountService cuentaOAuthService;

    private CuentaOAuthResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CuentaOAuthResponseDTO(1L, "google", "google_id_123", "test@gmail.com");
    }

    @Test
    @DisplayName("Debería retornar 200 con cuentas OAuth por usuario")
    void shouldReturn200WithOAuthAccountsWhenGetByUser() throws Exception {
        when(cuentaOAuthService.getByUser(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/oauth-accounts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].proveedor").value("google"));
    }

    @Test
    @DisplayName("Debería retornar 200 con cuenta OAuth cuando existe por ID")
    void shouldReturn200WhenOAuthAccountFoundById() throws Exception {
        when(cuentaOAuthService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/oauth-accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear una cuenta OAuth válida")
    void shouldReturn201WhenOAuthAccountCreatedSuccessfully() throws Exception {
        CuentaOAuthCreateDTO request = new CuentaOAuthCreateDTO(1L, "google", "google_id_new", "new@gmail.com");

        when(cuentaOAuthService.create(any(CuentaOAuthCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/oauth-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 al actualizar una cuenta OAuth")
    void shouldReturn200WhenOAuthAccountUpdatedSuccessfully() throws Exception {
        CuentaOAuthUpdateDTO request = new CuentaOAuthUpdateDTO("updated@gmail.com");

        when(cuentaOAuthService.update(eq(1L), any(CuentaOAuthUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/oauth-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar una cuenta OAuth")
    void shouldReturn204WhenOAuthAccountDeletedSuccessfully() throws Exception {
        doNothing().when(cuentaOAuthService).delete(1L);

        mockMvc.perform(delete("/api/oauth-accounts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar cuentas OAuth por usuario")
    void shouldReturn204WhenOAuthAccountsDeletedByUser() throws Exception {
        doNothing().when(cuentaOAuthService).deleteByUser(1L);

        mockMvc.perform(delete("/api/oauth-accounts/user/1"))
                .andExpect(status().isNoContent());
    }
}