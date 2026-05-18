package org.project.expressart.OpcionesComision.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Comision.domain.Comision;

import java.math.BigDecimal;


@Entity
@Table(name = "opciones_comision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpcionesComision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comision_id", nullable = false)
    private Comision comision;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "tiempo_entrega", nullable = false)
    private Integer tiempoEntrega;

    @Column(name = "numero_revisiones", nullable = false)
    private Integer numeroRevisiones = 1;

    @Column(name = "incluye_archivo_fuente", nullable = false)
    private Boolean incluyeArchivoFuente = false;

    @Column(name = "esta_activa", nullable = false)
    private Boolean estaActiva = true;

}