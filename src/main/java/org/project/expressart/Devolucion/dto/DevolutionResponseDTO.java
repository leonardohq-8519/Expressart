package org.project.expressart.Devolucion.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class DevolutionResponseDTO {
    private Long id;
    private Long ordenId;
    private EstadoDevolucion estado;
    private String motivo;
    private String respuestaArtista;
    private BigDecimal montoReembolso;
    private ZonedDateTime fechaSolicitud;
    private ZonedDateTime fechaResolucion;
}