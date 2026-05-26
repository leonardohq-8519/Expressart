package org.project.expressart.CuentaOAuth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.OAuthAccountService;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthUpdateDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
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

@WebMvcTest(controllers = CuentaOAuthController.class)
@WithMockUser
class CuentaOAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private OAuthAccountService OAuthAccountService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private CuentaOAuthResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new CuentaOAuthResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setProveedor("google");
        responseDTO.setProveedorId("sub-123");
    }

    @Test
    void shouldReturnAccountsWhenGetByUserAndExists() throws Exception {
        when(OAuthAccountService.getByUser(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/oauth-accounts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnEmptyListWhenGetByUserAndNoAccounts() throws Exception {
        when(OAuthAccountService.getByUser(99L)).thenReturn(List.of());

        mockMvc.perform(get("/api/oauth-accounts/user/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnAccountWhenGetByIdAndExists() throws Exception {
        when(OAuthAccountService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/oauth-accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        CuentaOAuthCreateDTO createDTO = new CuentaOAuthCreateDTO(1L, "google", "sub-123", "test@gmail.com");
        when(OAuthAccountService.create(any(CuentaOAuthCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/oauth-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn200WhenUpdateIsSuccessful() throws Exception {
        CuentaOAuthUpdateDTO updateDTO = new CuentaOAuthUpdateDTO("updated@gmail.com");
        when(OAuthAccountService.update(eq(1L), any(CuentaOAuthUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/oauth-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(OAuthAccountService).delete(1L);

        mockMvc.perform(delete("/api/oauth-accounts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenDeleteByUserIsSuccessful() throws Exception {
        doNothing().when(OAuthAccountService).deleteByUser(1L);

        mockMvc.perform(delete("/api/oauth-accounts/user/1"))
                .andExpect(status().isNoContent());
    }
}
