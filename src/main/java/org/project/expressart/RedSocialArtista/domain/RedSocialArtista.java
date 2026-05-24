package org.project.expressart.RedSocialArtista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;

@Entity
@Table(
        name = "redes_sociales_artista",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"perfil_artista_id", "plataforma"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedSocialArtista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_artista_id", nullable = false)
    private PerfilArtista perfilArtista;

    @Column(name = "plataforma", nullable = false, length = 50)
    private String plataforma;

    @Column(name = "url", nullable = false, length = 500)
    private String url;
}
