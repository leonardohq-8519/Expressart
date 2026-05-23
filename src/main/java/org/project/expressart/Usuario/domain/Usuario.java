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
    private PerfilCliente ArtistProfile;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilCliente ClientProfile;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CuentaOAuth> OAuthAccounts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "favorite_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Usuario> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notifications = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.registerDate = ZonedDateTime.now();
    }

}