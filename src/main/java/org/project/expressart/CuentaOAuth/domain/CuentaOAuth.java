package org.project.expressart.CuentaOAuth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Usuario.domain.Usuario;

@Entity
@Table(name = "cuentas_oauth",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"proveedor", "proveedor_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaOAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    //Luego hay que cambiarlo a Enum
    @Column(nullable = false)
    private String proveedor;

    @Column(name = "proveedor_id", nullable = false)
    private String proveedorId;

    @Column(name = "email_oauth", length = 255)
    private String emailOauth;


}
