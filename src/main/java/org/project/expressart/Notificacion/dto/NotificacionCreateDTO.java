package org.project.expressart.Notificacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Notificacion.domain.TipoNotificacion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCreateDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El tipo de notificacion es obligatorio")
    private TipoNotificacion tipo;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 100, message = "El titulo no puede superar los 100 caracteres")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    private String urlDestino;
}