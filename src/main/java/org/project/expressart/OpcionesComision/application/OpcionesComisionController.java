package org.project.expressart.OpcionesComision.application;

import org.project.expressart.OpcionesComision.application.OpcionesComisionService;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
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
    public ResponseEntity<List<CommissionOptionsResponseDTO>> getAll() {
        return ResponseEntity.ok(opcionesComisionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommissionOptionsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(opcionesComisionService.findById(id));
    }

    @GetMapping("/commission/{comisionId}")
    public ResponseEntity<List<CommissionOptionsResponseDTO>> getByComision(@PathVariable Long comisionId) {
        return ResponseEntity.ok(opcionesComisionService.findByComisionId(comisionId));
    }

    @PostMapping
    public ResponseEntity<CommissionOptionsResponseDTO> create(@RequestBody CommissionOptionsRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(opcionesComisionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommissionOptionsResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CommissionOptionsRequestDTO request) {
        return ResponseEntity.ok(opcionesComisionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opcionesComisionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}