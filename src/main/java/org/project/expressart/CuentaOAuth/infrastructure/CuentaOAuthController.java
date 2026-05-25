package org.project.expressart.CuentaOAuth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthUpdateDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oauth-accounts")
@RequiredArgsConstructor
public class CuentaOAuthController {

    private final OAuthAccountService cuentaOAuthService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CuentaOAuthResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cuentaOAuthService.getByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaOAuthResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaOAuthService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaOAuthResponseDTO> create(@RequestBody CuentaOAuthCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaOAuthService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaOAuthResponseDTO> update(@PathVariable Long id, @RequestBody CuentaOAuthUpdateDTO dto) {
        return ResponseEntity.ok(cuentaOAuthService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaOAuthService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteByUser(@PathVariable Long userId) {
        cuentaOAuthService.deleteByUser(userId);
        return ResponseEntity.noContent().build();
    }
}