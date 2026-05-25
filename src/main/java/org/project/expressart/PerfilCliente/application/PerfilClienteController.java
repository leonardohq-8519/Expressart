package org.project.expressart.PerfilCliente.application;

import org.project.expressart.PerfilCliente.domain.ClientProfileService;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-profiles")
public class PerfilClienteController {

    private final ClientProfileService clientProfileService;

    public PerfilClienteController(ClientProfileService clientProfileService) {
        this.clientProfileService = clientProfileService;
    }

    @GetMapping
    public ResponseEntity<List<ClientProfileResponseDTO>> getAll() {
        return ResponseEntity.ok(clientProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProfileResponseDTO> getById(@PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(clientProfileService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<ClientProfileResponseDTO> getByUsuario(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return ResponseEntity.ok(clientProfileService.findByUsuarioId(usuarioId));
    }

    @PostMapping("/user/{usuarioId}")
    public ResponseEntity<ClientProfileResponseDTO> create(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientProfileService.create(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}