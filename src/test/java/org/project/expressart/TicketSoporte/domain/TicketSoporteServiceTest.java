package org.project.expressart.TicketSoporte.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.TicketSoporte.infrastructure.TicketSoporteRepository;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketSoporteServiceTest {

    @Mock
    private TicketSoporteRepository ticketRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SupportTicketService supportTicketService;

    private TicketSoporte ticket;
    private SupportTicketRequestDTO requestDTO;
    private SupportTicketResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ticket = new TicketSoporte();
        ticket.setId(1L);
        ticket.setSubject("Fallo de carga");
        ticket.setStatus(EstadoTicket.ABIERTO);

        requestDTO = new SupportTicketRequestDTO();
        requestDTO.setAsunto("Fallo de carga"); // Mantiene nombres de tu DTO real

        responseDTO = new SupportTicketResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setAsunto("Fallo de carga");
    }

    @Test
    void findById_cuandoExiste_debeRetornarTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket, SupportTicketResponseDTO.class)).thenReturn(responseDTO);

        SupportTicketResponseDTO resultado = supportTicketService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getAsunto()).isEqualTo("Fallo de carga");
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> supportTicketService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}