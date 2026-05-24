package org.project.expressart.Pago.domain;

import lombok.RequiredArgsConstructor;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.infrastructure.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService{
    private final PagoRepository paymentRepository;
    public List<PaymentResponseDTO> findAll(){
    }
    public PaymentResponseDTO findById (Long id){
    }
    public PaymentResponseDTO findByOrderId (Long orderId){
    }
    public PaymentResponseDTO findByStripePaymentIntentId (String stripePaymentIntentId){
    }
    public PaymentResponseDTO create(PaymentRequestDTO request){
    }
    public PaymentResponseDTO  updateEstado (Long id, EstadoPago status){
    }
    public void delete (Long id){
    }

}