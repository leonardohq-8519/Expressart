package org.project.expressart.TicketSoporte.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.project.expressart.TicketSoporte.infrastructure.TicketSoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketService {
    @Autowired
    private final TicketSoporteRepository supportTicketRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SupportTicketResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return supportTicketRepository.findAllBy(pageable);
    }
    public SupportTicketResponseDTO  findById (Long id){
        TicketSoporte ticket = supportTicketRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO findByUsuarioId (Long userId){
        TicketSoporte ticket = supportTicketRepository.findByUsuarioId(userId).orElseThrow(()-> new ResourceNotFoundEXception("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO findByEstado (EstadoTicket status){
        TicketSoporte ticket = supportTicketRepository.findByEstado(status).orElseThrow(()-> new ResourceNotFoundEXception("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO findByEstadoAndCategoria (EstadoTicket status, CategoriaTicket category){
        TicketSoporte ticket = supportTicketRepository.findByEstadoAndCategoria(status, category).orElseThrow(()-> new ResourceNotFoundEXception("Ticket not found"));
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO create(SupportTicketRequestDTO request)throws BadRequestException {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setSubject(request.getAsunto());
        ticket.setCategory(request.getCategoria());
        ticket.setDescription(request.getDescripcion());
        // ticket.setUser(request.getUsuarioId());
        // ticket.setOrder(request.getOrdenId());
        supportTicketRepository.save(ticket);
        return modelMapper.map(ticket, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO updateStatus(Long id, EstadoTicket status){
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketSoporte.setStatus(status);
        supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(ticketSoporte, SupportTicketResponseDTO.class);
    }
    public SupportTicketResponseDTO addResponse (Long id, String answer){
        TicketSoporte ticketSoporte = supportTicketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketSoporte.setAnswer(answer);
        supportTicketRepository.save(ticketSoporte);
        return modelMapper.map(ticketSoporte, SupportTicketResponseDTO.class);
    }
    public void delete (Long id){
        if (supportTicketRepository.existsById(id))
            supportTicketRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Ticket with ID " + id + " doesn't exist");
    }
}
