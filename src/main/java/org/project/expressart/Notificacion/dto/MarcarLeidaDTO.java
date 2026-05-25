package org.project.expressart.Notificacion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarcarLeidaDTO {

    @NotNull(message = "La lista de IDs es obligatoria")
    @NotEmpty(message = "Debe incluir al menos un ID")
    private List<Long> notificacionIds;
}