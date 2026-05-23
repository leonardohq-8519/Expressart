package org.project.expressart.OpcionesComision.application;

import org.project.expressart.OpcionesComision.application.OpcionesComisionService;
import org.project.expressart.OpcionesComision.dto.OpcionesComisionRequestDTO;
import org.project.expressart.OpcionesComision.dto.OpcionesComisionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commission-options")
public class OpcionesComisionController {

    private final OpcionesComisionService opcionesComisionService;

    public OpcionesComisionController(OpcionesComisionService opcionesComisionService) {
        this.opcionesComisionService = opcionesComisionService;
    }

    @GetMapping
    public ResponseEntity<List<OpcionesComisionResponseDTO>> getAll() {
        return ResponseEntity.ok(opcionesComisionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpcionesComisionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(opcionesComisionService.findById(id));
    }

    @GetMapping("/commission/{comisionId}")
    public ResponseEntity<List<OpcionesComisionResponseDTO>> getByComision(@PathVariable Long comisionId) {
        return ResponseEntity.ok(opcionesComisionService.findByComisionId(comisionId));
    }

    @PostMapping
    public ResponseEntity<OpcionesComisionResponseDTO> create(@RequestBody OpcionesComisionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(opcionesComisionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpcionesComisionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OpcionesComisionRequestDTO request) {
        return ResponseEntity.ok(opcionesComisionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opcionesComisionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}