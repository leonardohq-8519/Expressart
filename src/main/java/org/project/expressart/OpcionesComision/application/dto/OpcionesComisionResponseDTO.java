package org.project.expressart.OpcionesComision.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OpcionesComisionResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer tiempoEntrega;
    private Integer numeroRevisiones;
    private Boolean incluyeArchivoFuente;
    private Boolean estaActiva;
}