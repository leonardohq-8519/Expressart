package org.project.expressart.OpcionesComision.application;

import org.project.expressart.OpcionesComision.domain.CommissionOptionsService;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commission-options")
public class OpcionesComisionController {

    private final CommissionOptionsService commissionOptionsService;

    public OpcionesComisionController(CommissionOptionsService commissionOptionsService) {
        this.commissionOptionsService = commissionOptionsService;
    }

    @GetMapping
    public ResponseEntity<List<CommissionOptionsResponseDTO>> getAll() {
        return ResponseEntity.ok(commissionOptionsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommissionOptionsResponseDTO> getById(@PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(commissionOptionsService.findById(id));
    }

    @GetMapping("/commission/{comisionId}")
    public ResponseEntity<List<CommissionOptionsResponseDTO>> getByComision(@PathVariable Long comisionId) throws ResourceNotFoundException {
        return ResponseEntity.ok(commissionOptionsService.findByComisionId(comisionId));
    }

    @PostMapping
    public ResponseEntity<CommissionOptionsResponseDTO> create(@RequestBody CommissionOptionsRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commissionOptionsService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommissionOptionsResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CommissionOptionsRequestDTO request)throws ResourceNotFoundException {
        return ResponseEntity.ok(commissionOptionsService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commissionOptionsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}