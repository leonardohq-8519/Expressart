package org.project.expressart.Orden.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private Long artistaId;
    private Long clienteId;
    private Long opcionComisionId;
    private String descripcionTrabajo;
    private java.math.BigDecimal precioFinal;
}