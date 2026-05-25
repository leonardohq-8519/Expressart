package org.project.expressart.PerfilArtista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Usuario.domain.Usuario;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfiles_artista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilArtista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "disponible", nullable = false)
    private Boolean comsDisponibles = true;

    @Column(name = "tiempo_entrega_promedio")
    private Integer tiempoEntregaPromedio;

    @Column(name = "rating_promedio", precision = 3, scale = 2)
    private BigDecimal ratingPromedio;

    @Column(nullable = false)
    private Integer totalReviews = 0;

    @Column(nullable = false)
    private Integer ordenesCompletadas = 0;

    @Column(nullable = false)
    private ZonedDateTime fechaCreacion;

    @Column(name = "stripe_id")
    private String stripeId;

    @Column(name = "stripe_onboarding", nullable = false)
    private Boolean stripeVerificado = false;

    @ManyToMany
    @JoinTable(
            name = "perfil_artista_categoria",
            joinColumns = @JoinColumn(name = "perfil_artista_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
    }
}
