package org.project.expressart.PerfilCliente.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class PerfilClienteResponseDTO {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private BigDecimal ratingPromedio;
    private Integer totalResenas;
    private Integer ordenesRealizadas;
    private ZonedDateTime fechaCreacion;
}