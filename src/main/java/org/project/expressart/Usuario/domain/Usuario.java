package org.project.expressart.Usuario.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String email;
    private String password;
    private String avatar_url;
    private String biography;
    private boolean isActive;
    private boolean isVerified;
    private ZonedDateTime registerDate;

    @ManyToMany
    private List<Usuario> favorites;

    @OneToMany(mappedBy = "usuario")
    private List<CuentaOAuth> OAuthAccounts = new ArrayList<>();

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Long getUsuarioId() {
        return this.id;
    }
}