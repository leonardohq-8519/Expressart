package org.project.expressart.CuentaOAuth.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.CuentaOAuth.domain.OAuthAccountService;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.dto.CuentaOAuthUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oauth-accounts")
@RequiredArgsConstructor
public class CuentaOAuthController {

    private final OAuthAccountService OAuthAccountService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CuentaOAuthResponseDTO>> getByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(OAuthAccountService.getByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaOAuthResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(OAuthAccountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaOAuthResponseDTO> create(
            @RequestBody CuentaOAuthCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OAuthAccountService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaOAuthResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CuentaOAuthUpdateDTO dto) {
        return ResponseEntity.ok(OAuthAccountService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        OAuthAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteByUser(@PathVariable Long userId) {
        OAuthAccountService.deleteByUser(userId);
        return ResponseEntity.noContent().build();
    }
}