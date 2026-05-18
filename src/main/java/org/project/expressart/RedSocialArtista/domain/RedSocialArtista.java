package org.project.expressart.RedSocialArtista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private PerfilCliente perfilArtista;

    @Column(nullable = false, length = 50)
    private String plataforma;

    @Column(nullable = false, length = 500)
    private String url;
}
