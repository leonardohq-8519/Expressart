package org.project.expressart.Pago.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Orden.domain.Orden;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden orden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "monto_artista", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoArtista;

    @Column(name = "monto_comision_plataforma", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoComisionPlataforma;

    @Column(name = "stripe_payment_intent_id", unique = true, nullable = false, length = 255)
    private String stripePaymentIntentId;

    @Column(name = "stripe_transfer_id", unique = true, length = 255)
    private String stripeTransferId;

    // Timestamps de eventos de pago
    @Column(name = "fecha_pago")
    private ZonedDateTime fechaPago;

    @Column(name = "fecha_transferencia")
    private ZonedDateTime fechaTransferencia;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
    }
}