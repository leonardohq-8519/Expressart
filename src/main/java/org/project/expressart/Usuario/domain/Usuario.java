package org.project.expressart.Usuario.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;
import org.project.expressart.Notificacion.domain.Notificacion;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "username",nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "fullname",nullable = false, length = 100)
    private String name;

    //Hay que ver como adaptarlo para OAuth
    private String password;

    @Column(name = "avatar_url")
    private String avatar_url;

    @Column(name = "biography",columnDefinition = "TEXT")
    private String biography;

    @Column(name = "register_date", nullable = false)
    private ZonedDateTime registerDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "token_version")
    private Long token;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilCliente perfilArtista;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilCliente perfilCliente;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CuentaOAuth> cuentasOAuth = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private List<Usuario> favoritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.registerDate = ZonedDateTime.now();
    }

    /*
    stringnombre
    string nombre_usuario
    string biografia
    string correo
    string contraseña
    ____ foto_perfil
    ____ banner
    daComisiones?/Estado
    lista seguidos
     */
}