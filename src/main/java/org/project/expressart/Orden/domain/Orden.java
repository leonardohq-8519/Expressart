package org.project.expressart.Orden.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Devolucion.domain.Devolucion;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.Pago.domain.Pago;
import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.project.expressart.ResenaCliente.domain.ResenaCliente;
import org.project.expressart.Usuario.domain.Usuario;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Usuario artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opcion_comision_id", nullable = false)
    private OpcionesComision opcionComision;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    @Column(name = "precio_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioFinal;

    @Column(name = "descripcion_trabajo", nullable = false, columnDefinition = "TEXT")
    private String descripcionTrabajo;

    @Column(name = "archivo_entrega_url", length = 500)
    private String archivoEntregaUrl;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @Column(name = "fecha_limite", nullable = false, updatable = false)
    private ZonedDateTime fechaLimite;

    @Column(name = "fecha_completada")
    private ZonedDateTime fechaCompletada;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private Pago pago;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private Devolucion devolucion;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private Chat chat;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private ResenaArtista resenaArtista;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private ResenaCliente resenaCliente;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
        this.fechaLimite = this.fechaCreacion.plusDays(opcionComision.getTiempoEntrega());
    }
}
