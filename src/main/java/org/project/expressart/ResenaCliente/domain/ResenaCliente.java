package org.project.expressart.ResenaCliente.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Usuario.domain.Usuario;

import java.time.ZonedDateTime;

@Entity
@Table(name = "resenas_artista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResenaCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Usuario artista;

    @Column(name = "puntuacion",nullable = false)
    private Short puntuacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
        if (this.puntuacion < 1 || this.puntuacion > 5) {
            throw new IllegalArgumentException(
                    "La puntuación debe estar entre 1 y 5"
            );
        }
    }
}
