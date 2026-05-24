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
    private Usuario user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    private Orden order; //Puede ser NULL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTicket status = EstadoTicket.ABIERTO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaTicket category;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    @Column(name = "fecha_resolucion")
    private ZonedDateTime resolutionDate;

    @PrePersist
    protected void onCreate(){
        this.creationDate = ZonedDateTime.now();
    }
}