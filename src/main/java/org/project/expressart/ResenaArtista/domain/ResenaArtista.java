package org.project.expressart.ResenaArtista.domain;

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
public class ResenaArtista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Usuario artist;

    @Column(nullable = false)
    private Short score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    @PrePersist
    protected void onCreate(){
        this.creationDate = ZonedDateTime.now();
        if (this.score < 1 || this.score > 5) {
            throw new IllegalArgumentException(
                    "La puntuación debe estar entre 1 y 5"
            );
        }
    }
}
