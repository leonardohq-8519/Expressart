package org.project.expressart.TicketSoporte.domain;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.project.expressart.TicketSoporte.events.TicketCreatedEvent;
import org.project.expressart.TicketSoporte.infrastructure.TicketSoporteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketService {
    private final TicketSoporteRepository supportTicketRepository;
    private final UsuarioRepository userRepository;
    private final OrdenRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;
    @Autowired
    private ModelMapper modelMapper;

    public List<SupportTicketResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return supportTicketRepository.findAllBy(pageable);
    }
    public SupportTicketResponseDTO  findById (Long id)throws ResourceNotFoundException{
        TicketSoporte ticket = supportTicketRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public List<SupportTicketResponseDTO> findByUsuarioId (Long userId) throws ResourceNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        List<TicketSoporte> tickets = supportTicketRepository.findByUsuarioId(userId);

        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found for user ID: " + userId);
        }

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, SupportTicketResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<SupportTicketResponseDTO> findByEstado (EstadoTicket status) throws ResourceNotFoundException {
        List<TicketSoporte> tickets = supportTicketRepository.findByEstado(status);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found for status: " + status);
        }
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, SupportTicketResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<SupportTicketResponseDTO> findByEstadoAndCategoria (EstadoTicket status, CategoriaTicket category) throws ResourceNotFoundException {
        List<TicketSoporte> tickets = supportTicketRepository.findByEstadoAndCategoria(status, category);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("No tickets found for status: " + status + "or category: " + category);
        }
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, SupportTicketResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public SupportTicketResponseDTO create(SupportTicketRequestDTO request)throws BadRequestException {
        TicketSoporte ticket = new TicketSoporte();
        Usuario user = userRepository.findById(request.getUsuarioId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ticket.setUser(user);
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        ticket.setOrder(order);
        ticket.setSubject(request.getAsunto());
        ticket.setCategory(request.getCategoria());
        ticket.setDescription(request.getDescripcion());
        ticket.setStatus(EstadoTicket.values()[0]);
        supportTicketRepository.save(ticket);
        eventPublisher.publishEvent(new TicketCreatedEvent(ticket.getId(), ticket.getSubject()));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO updateStatus(Long id, EstadoTicket status)throws ResourceNotFoundException{
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketSoporte.setStatus(status);
        supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(ticketSoporte, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO addResponse (Long id, String answer)throws ResourceNotFoundException{
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketSoporte.setAnswer(answer);
        supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(ticketSoporte, SupportTicketResponseDTO.class);
    }
    @Transactional
    public void delete (Long id){
        if (supportTicketRepository.existsById(id))
            supportTicketRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Ticket with ID " + id + " doesn't exist");
    }
}
