package org.project.expressart.CuentaOAuth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaOAuthResponseDTO {
    private Long id;
    private String proveedor;
    private String proveedorId;
    private String emailOauth;
}