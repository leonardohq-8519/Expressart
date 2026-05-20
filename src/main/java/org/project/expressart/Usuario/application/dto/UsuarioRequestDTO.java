package org.project.expressart.Usuario.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nombre_usuario;
    private String email;
    private String nombre;
    private String contraseña;
    private String avatar_url;
    private String biografia;
}