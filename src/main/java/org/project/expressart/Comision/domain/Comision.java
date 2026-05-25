package org.project.expressart.Comision.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.Tags.domain.Tags;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "comisiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_artista_id", nullable = false)
    private PerfilArtista perfilArtista;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "portada_url", length = 500)
    private String portadaUrl;

    @Column(name = "esta_activa", nullable = false)
    private Boolean estaActiva = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @OneToMany(mappedBy = "comision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcionesComision> opciones = new ArrayList<>();

    @OneToMany(mappedBy = "comision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenComision> imagenes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comision_categoria",
            joinColumns = @JoinColumn(name = "comision_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comision_tag",
            joinColumns = @JoinColumn(name = "comision_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tags> tags = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = ZonedDateTime.now();
    }
}