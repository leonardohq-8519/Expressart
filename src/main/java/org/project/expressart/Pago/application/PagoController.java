package org.project.expressart.Pago.application;

import org.project.expressart.Pago.application.PagoService;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.domain.EstadoPago;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(pagoService.findByOrderId(orderId));
    }

    @GetMapping("/stripe/{stripePaymentIntentId}")
    public ResponseEntity<PaymentResponseDTO> getByStripeIntent(@PathVariable String stripePaymentIntentId) {
        return ResponseEntity.ok(pagoService.findByStripePaymentIntentId(stripePaymentIntentId));
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EstadoPago estado) {
        return ResponseEntity.ok(pagoService.updateStatus(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}