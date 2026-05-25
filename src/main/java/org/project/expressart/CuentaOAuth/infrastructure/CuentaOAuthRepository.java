package org.project.expressart.CuentaOAuth.infrastructure;

import org.project.expressart.CuentaOAuth.domain.CuentaOAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaOAuthRepository extends JpaRepository<CuentaOAuth, Long> {}


