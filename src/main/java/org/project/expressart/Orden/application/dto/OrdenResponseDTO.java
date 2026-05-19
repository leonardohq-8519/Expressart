package org.project.expressart.Orden.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.Orden.domain.EstadoOrden;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class OrdenResponseDTO {
    private Long id;
    private Long artistaId;
    private String nombreArtista;
    private Long clienteId;
    private String nombreCliente;
    private Long opcionComisionId;
    private String nombreOpcionComision;
    private EstadoOrden estado;
    private BigDecimal precioFinal;
    private String descripcionTrabajo;
    private String archivoEntregaUrl;
    private ZonedDateTime fechaCreacion;
    private ZonedDateTime fechaLimite;
    private ZonedDateTime fechaCompletada;

}