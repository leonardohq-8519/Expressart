package org.project.expressart.Usuario.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class PerfilArtistaResponseDTO {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private Boolean comsDisponibles;
    private Integer tiempoEntregaPromedio;
    private BigDecimal ratingPromedio;
    private Integer totalReviews;
    private Integer ordenesCompletadas;
    private ZonedDateTime fechaCreacion;
    private Boolean stripeVerificado;
    private List<String> categorias;

}