package org.project.expressart.ResenaCliente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResenaClienteRequestDTO {
    private Long ordenId;
    private Long clienteId;
    private Long artistaId;
    private Short puntuacion;
    private String comentario;
}