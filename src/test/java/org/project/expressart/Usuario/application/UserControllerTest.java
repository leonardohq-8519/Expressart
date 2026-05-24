package org.project.expressart.Usuario.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Usuario.domain.UserService;
import org.project.expressart.Usuario.dto.UserRequestDTO;
import org.project.expressart.Usuario.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new UserRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setEmail("test@email.com");
        requestDTO.setName("Test User");
        requestDTO.setPassword("password123");

        responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUsername("testuser");
        responseDTO.setEmail("test@email.com");
        responseDTO.setName("Test User");
    }

    @Test
    void getAll_debeRetornar200_conListaDeUsuarios() throws Exception {
        when(userService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("test@email.com"));
    }

    @Test
    void getById_debeRetornar200_cuandoExisteUsuario() throws Exception {
        when(userService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getByEmail_debeRetornar200_cuandoExisteEmail() throws Exception {
        when(userService.findByEmail("test@email.com")).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/email/test@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void create_debeRetornar201_cuandoDatosValidos() throws Exception {
        when(userService.create(any(UserRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void update_debeRetornar200_cuandoActualizaCorrectamente() throws Exception {
        when(userService.update(eq(1L), any(UserRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/usuarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_debeRetornar204_cuandoEliminaCorrectamente() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/usuarios/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(1L);
    }

    @Test
    void getSeguidores_debeRetornar200_conListaDeSeguidores() throws Exception {
        when(userService.findSeguidoresByArtistaId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/usuarios/seguidores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }
}