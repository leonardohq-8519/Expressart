package org.project.expressart.PerfilCliente.application;

import org.project.expressart.PerfilCliente.domain.PerfilClienteService;
import org.project.expressart.PerfilCliente.dto.PerfilClienteResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-profiles")
public class PerfilClienteController {

    private final PerfilClienteService perfilClienteService;

    public PerfilClienteController(PerfilClienteService perfilClienteService) {
        this.perfilClienteService = perfilClienteService;
    }

    @GetMapping
    public ResponseEntity<List<PerfilClienteResponseDTO>> getAll() {
        return ResponseEntity.ok(perfilClienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilClienteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(perfilClienteService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<PerfilClienteResponseDTO> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(perfilClienteService.findByUsuarioId(usuarioId));
    }

    @PostMapping("/user/{usuarioId}")
    public ResponseEntity<PerfilClienteResponseDTO> create(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilClienteService.create(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        perfilClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}