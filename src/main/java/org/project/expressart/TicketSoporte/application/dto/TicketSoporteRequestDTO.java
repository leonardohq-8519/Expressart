package org.project.expressart.TicketSoporte.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;

@Getter
@Setter
public class TicketSoporteRequestDTO {
    private Long usuarioId;
    private Long ordenId;
    private CategoriaTicket categoria;
    private String asunto;
    private String descripcion;
}