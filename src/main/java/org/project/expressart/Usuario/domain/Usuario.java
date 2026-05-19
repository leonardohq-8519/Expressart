package org.project.expressart.Usuario.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String nombre_usuario;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String nombre;

    //Hay que ver como adaptarlo para OAuth
    private String contraseña;

    private String avatar_url;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(nullable = false)
    private ZonedDateTime fechaRegistro;

    private Long token;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilCliente perfilArtista;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilCliente perfilCliente;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CuentaOAuth> cuentasOAuth = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.fechaRegistro = ZonedDateTime.now();
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