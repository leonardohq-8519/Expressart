package org.project.expressart.Devolucion.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DevolutionRequestDTO {
    private Long ordenId;
    private String motivo;
    private BigDecimal montoReembolso;
}