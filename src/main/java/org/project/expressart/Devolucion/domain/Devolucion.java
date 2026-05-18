package org.project.expressart.Devolucion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Orden.domain.Orden;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "devoluciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoDevolucion estado = EstadoDevolucion.SOLICITADA;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "respuesta_artista", columnDefinition = "TEXT")
    private String respuestaArtista;

    @Column(name = "monto_reembolso", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoReembolso;

    @Column(name = "stripe_id_reembolso", unique = true)
    private String stripeIdReembolso;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private ZonedDateTime fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private ZonedDateTime fechaResolucion;

    @PrePersist
    protected void onCreate() {
        this.fechaSolicitud = ZonedDateTime.now();
    }


}