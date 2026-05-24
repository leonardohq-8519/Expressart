package org.project.expressart.Pago.application;

import org.project.expressart.Pago.domain.PaymentService;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.domain.EstadoPago;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PagoController {

    private final PaymentService paymentService;

    public PagoController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getByOrder(@PathVariable Long orderId)throws ResourceNotFoundException {
        return ResponseEntity.ok(paymentService.findByOrderId(orderId));
    }

    @GetMapping("/stripe/{stripePaymentIntentId}")
    public ResponseEntity<PaymentResponseDTO> getByStripeIntent(@PathVariable String stripePaymentIntentId) throws ResourceNotFoundException{
        return ResponseEntity.ok(paymentService.findByStripePaymentIntentId(stripePaymentIntentId));
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updateStatus (
            @PathVariable Long id,
            @RequestParam EstadoPago estado) throws ResourceNotFoundException {
        return ResponseEntity.ok(paymentService.updateStatus(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}