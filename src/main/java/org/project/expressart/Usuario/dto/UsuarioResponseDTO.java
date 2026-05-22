package org.project.expressart.Usuario.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nombre_usuario;
    private String email;
    private String nombre;
    private String avatar_url;
    private String biografia;
    private ZonedDateTime fechaRegistro;
}