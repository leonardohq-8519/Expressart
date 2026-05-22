package org.project.expressart.TicketSoporte.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Usuario.domain.Usuario;

import java.time.ZonedDateTime;

@Entity
@Table(name = "tickets_soporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    private Orden orden; //Puede ser NULL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTicket estado = EstadoTicket.ABIERTO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaTicket categoria;

    @Column(nullable = false, length = 255)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @Column(name = "fecha_resolucion")
    private ZonedDateTime fechaResolucion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
    }
}