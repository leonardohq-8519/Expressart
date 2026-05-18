package org.project.expressart.PerfilCliente.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Usuario.domain.Usuario;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "perfiles_clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "rating_promedio", precision = 3, scale = 2)
    private BigDecimal ratingPromedio;

    @Column(name = "total_resenas", nullable = false)
    private Integer totalResenas = 0;

    @Column(name = "ordenes_realizadas", nullable = false)
    private Integer ordenesRealizadas = 0;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
    }
}
