package org.project.expressart.TicketSoporte.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.SupportTicketService;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketSoporteController.class)
@WithMockUser
class TicketSoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private SupportTicketService supportTicketService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private SupportTicketRequestDTO requestDTO;
    private SupportTicketResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new SupportTicketRequestDTO();
        requestDTO.setAsunto("Error en pasarela");
        requestDTO.setDescripcion("No procesa pagos con tarjeta");

        responseDTO = new SupportTicketResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setAsunto("Error en pasarela");
    }

    @Test
    void getAll_debeRetornar200_conListaDeTickets() throws Exception {
        when(supportTicketService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/support-tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].asunto").value("Error en pasarela"));
    }

    @Test
    void getById_debeRetornar200_cuandoExisteTicket() throws Exception {
        when(supportTicketService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/support-tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.asunto").value("Error en pasarela"));
    }

    @Test
    void getByUsuario_debeRetornar200_conTicketsDelUsuario() throws Exception {
        when(supportTicketService.findByUsuarioId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/support-tickets/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getByEstado_debeRetornar200_conTicketsFiltrados() throws Exception {
        EstadoTicket estadoDummy = EstadoTicket.values()[0];
        when(supportTicketService.findByEstado(estadoDummy)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/support-tickets/status/" + estadoDummy.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].asunto").value("Error en pasarela"));
    }

    @Test
    void getByEstadoAndCategoria_debeRetornar200_conTicketsFiltrados() throws Exception {
        EstadoTicket estadoDummy = EstadoTicket.values()[0];
        CategoriaTicket categoriaDummy = CategoriaTicket.values()[0];

        when(supportTicketService.findByEstadoAndCategoria(estadoDummy, categoriaDummy))
                .thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/support-tickets/status/" + estadoDummy.name() + "/category/" + categoriaDummy.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].asunto").value("Error en pasarela"));
    }

    @Test
    void create_debeRetornar201_cuandoDatosValidos() throws Exception {
        when(supportTicketService.create(any(SupportTicketRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/support-tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.asunto").value("Error en pasarela"));
    }

    @Test
    void updateStatus_debeRetornar200_cuandoSeActualiza() throws Exception {
        EstadoTicket estadoDummy = EstadoTicket.values()[1];
        when(supportTicketService.updateStatus(1L, estadoDummy)).thenReturn(responseDTO);

        mockMvc.perform(patch("/support-tickets/1/status")
                        .param("estado", estadoDummy.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addResponse_debeRetornar200_cuandoSeAgregaRespuesta() throws Exception {
        when(supportTicketService.addResponse(1L, "Solucionado")).thenReturn(responseDTO);

        mockMvc.perform(patch("/support-tickets/1/response")
                        .param("respuesta", "Solucionado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_debeRetornar204_cuandoSeElimina() throws Exception {
        doNothing().when(supportTicketService).delete(1L);

        mockMvc.perform(delete("/support-tickets/1"))
                .andExpect(status().isNoContent());
    }
}