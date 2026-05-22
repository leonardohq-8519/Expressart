package org.project.expressart.Devolucion.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DevolucionRequestDTO {
    private Long ordenId;
    private String motivo;
    private BigDecimal montoReembolso;
}