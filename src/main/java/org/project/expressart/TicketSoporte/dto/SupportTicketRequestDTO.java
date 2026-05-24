package org.project.expressart.TicketSoporte.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;

@Getter
@Setter
public class SupportTicketRequestDTO {
    private Long usuarioId;
    private Long ordenId;
    private CategoriaTicket categoria;
    private String asunto;
    private String descripcion;
}