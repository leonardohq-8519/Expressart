package org.project.expressart.Usuario.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class Usuario{
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre_usuario;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    //Hay que ver como adaptarlo para OAuth
    private String contraseña;

    private String avatar_url;

    private String biografia;

    @Column(nullable = false)
    private ZonedDateTime fecha_registro;


    private Long token;
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