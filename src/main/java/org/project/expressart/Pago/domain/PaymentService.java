package org.project.expressart.Pago.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.infrastructure.PagoRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final PagoRepository pagoRepository;

    public List<PaymentResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Pago> pagos = pagoRepository.findAll(pageable).getContent();
        return convertToDtoList(pagos);
    }

    public PaymentResponseDTO findById(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Payment not found"));
        return modelMapper.map(pago, PaymentResponseDTO.class);
    }

    public PaymentResponseDTO findByOrderId(Long orderId) {
        Pago pago = pagoRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundEXception("Payment not found for order ID " + orderId));
        return modelMapper.map(pago, PaymentResponseDTO.class);
    }

    public PaymentResponseDTO findByStripePaymentIntentId(String stripePaymentIntentId) {
        Pago pago = pagoRepository.findByStripePaymentIntentId(stripePaymentIntentId)
                .orElseThrow(() -> new ResourceNotFoundEXception("Payment not found for Stripe Intent " + stripePaymentIntentId));
        return modelMapper.map(pago, PaymentResponseDTO.class);
    }

    public PaymentResponseDTO create(PaymentRequestDTO request) {
        Pago pago = modelMapper.map(request, Pago.class);
        Pago savedPayment = pagoRepository.save(pago);
        return modelMapper.map(savedPayment, PaymentResponseDTO.class);
    }

    public PaymentResponseDTO updateStatus(Long id, EstadoPago estado) {
        Pago existingPayment = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Payment not found"));

        existingPayment.setEstado(estado); // Cambia por setEstadoPago si tu atributo se llama diferente

        Pago updatedPayment = pagoRepository.save(existingPayment);
        return modelMapper.map(updatedPayment, PaymentResponseDTO.class);
    }

    public void delete(Long id) {
        if (pagoRepository.existsById(id))
            pagoRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Payment with ID " + id + " doesn't exist");
    }

    private List<PaymentResponseDTO> convertToDtoList(List<Pago> pagos) {
        return pagos.stream()
                .map(pago -> modelMapper.map(pago, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }
}