package org.project.expressart.PerfilCliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClientProfileRequestDTO {
    private Long usuarioId;
    private BigDecimal ratingPromedio;
    private Integer totalResenas;
    private Integer ordenesRealizadas;
}