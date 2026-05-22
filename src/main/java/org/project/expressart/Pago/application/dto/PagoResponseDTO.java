package org.project.expressart.Pago.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Pago.domain.EstadoPago;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class PagoResponseDTO {
    private Long id;
    private Long ordenId;
    private EstadoPago estado;
    private BigDecimal monto;
    private BigDecimal montoArtista;
    private BigDecimal montoComisionPlataforma;
    private ZonedDateTime fechaPago;
    private ZonedDateTime fechaTransferencia;
    private ZonedDateTime fechaCreacion;
}