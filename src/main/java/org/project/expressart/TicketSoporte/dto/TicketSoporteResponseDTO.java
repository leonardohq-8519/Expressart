package org.project.expressart.TicketSoporte.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import java.time.ZonedDateTime;

@Getter
@Setter
public class TicketSoporteResponseDTO {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private Long ordenId;
    private EstadoTicket estado;
    private CategoriaTicket categoria;
    private String asunto;
    private String descripcion;
    private String respuesta;
    private ZonedDateTime fechaCreacion;
    private ZonedDateTime fechaResolucion;
}