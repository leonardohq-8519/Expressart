package org.project.expressart.TicketSoporte.domain;

import lombok.RequiredArgsConstructor;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketService {
    public List<SupportTicketResponseDTO> findAll(){
    }
    public SupportTicketResponseDTO  findById (Long id){
    }
    public SupportTicketResponseDTO findByUsuarioId (Long userId){
    }
    public SupportTicketResponseDTO findByEstado (EstadoTicket status){
    }
    public SupportTicketResponseDTO findByEstadoAndCategoria (EstadoTicket estado, CategoriaTicket categoria){
    }
    public SupportTicketResponseDTO create(SupportTicketRequestDTO request){
    }
    public SupportTicketResponseDTO updateStatus(Long id, EstadoTicket status){
    }
    public SupportTicketResponseDTO addResponse (Long id, String answer){
    }
    public void delete (Long id){
    }
}
