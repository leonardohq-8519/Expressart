package org.project.expressart.TicketSoporte.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.project.expressart.TicketSoporte.infrastructure.TicketSoporteRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    @Autowired
    private final TicketSoporteRepository supportTicketRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SupportTicketResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TicketSoporte> tickets = supportTicketRepository.findAll(pageable).getContent();
        return convertToDtoList(tickets);
    }

    public SupportTicketResponseDTO findById(Long id) {
        TicketSoporte ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }

    public List<SupportTicketResponseDTO> findByUsuarioId(Long userId) {
        List<TicketSoporte> tickets = supportTicketRepository.findByUsuarioId(userId);
        return convertToDtoList(tickets);
    }

    public List<SupportTicketResponseDTO> findByEstado(EstadoTicket status) {
        List<TicketSoporte> tickets = supportTicketRepository.findByEstado(status);
        return convertToDtoList(tickets);
    }

    public List<SupportTicketResponseDTO> findByEstadoAndCategoria(EstadoTicket status, CategoriaTicket category) {
        List<TicketSoporte> tickets = supportTicketRepository.findByEstadoAndCategoria(status, category);
        return convertToDtoList(tickets);
    }

    public SupportTicketResponseDTO create(SupportTicketRequestDTO request) {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setSubject(request.getAsunto());
        ticket.setCategory(request.getCategoria());
        ticket.setDescription(request.getDescripcion());

        // Descomenta y adapta si añades las relaciones con Usuario u Orden:
        // ticket.setUser(request.getUsuarioId());
        // ticket.setOrder(request.getOrdenId());

        TicketSoporte savedTicket = supportTicketRepository.save(ticket);
        return modelMapper.map(savedTicket, SupportTicketResponseDTO.class);
    }

    public SupportTicketResponseDTO updateStatus(Long id, EstadoTicket status) {
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Ticket not found"));

        ticketSoporte.setStatus(status);
        TicketSoporte updatedTicket = supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(updatedTicket, SupportTicketResponseDTO.class);
    }

    public SupportTicketResponseDTO addResponse(Long id, String answer) {
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Ticket not found"));

        ticketSoporte.setAnswer(answer);
        TicketSoporte updatedTicket = supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(updatedTicket, SupportTicketResponseDTO.class);
    }

    public void delete(Long id) {
        if (supportTicketRepository.existsById(id))
            supportTicketRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Ticket with ID " + id + " doesn't exist");
    }

    private List<SupportTicketResponseDTO> convertToDtoList(List<TicketSoporte> tickets) {
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, SupportTicketResponseDTO.class))
                .collect(Collectors.toList());
    }
}