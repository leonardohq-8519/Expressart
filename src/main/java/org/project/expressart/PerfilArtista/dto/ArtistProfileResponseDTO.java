package org.project.expressart.PerfilArtista.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class ArtistProfileResponseDTO {
    private Long id;
    private Long usuarioId;        // solo el ID, no el objeto completo
    private String nombreUsuario;  // dato útil del usuario
    private Boolean comsDisponibles;
    private Integer tiempoEntregaPromedio;
    private BigDecimal ratingPromedio;
    private Integer totalReviews;
    private Integer ordenesCompletadas;
    private ZonedDateTime fechaCreacion;
    private Boolean stripeVerificado;
    private List<String> categorias; // nombres de las categorías
    // No incluimos stripeId por seguridad
}