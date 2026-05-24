package org.project.expressart.Pago.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.infrastructure.PagoRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService{
    @Autowired
    private final PagoRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<PaymentResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return paymentRepository.findAllBy(pageable);
    }
    public PaymentResponseDTO findById (Long id){
        Pago payment = paymentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Payment not found"));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public PaymentResponseDTO findByOrderId (Long orderId){
        Pago payment = paymentRepository.findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundEXception("Payment not found"));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public PaymentResponseDTO findByStripePaymentIntentId (String stripePaymentIntentId){
    }
    public PaymentResponseDTO create(PaymentRequestDTO request){
    }
    public PaymentResponseDTO  updateEstado (Long id, EstadoPago status){
    }
    public void delete (Long id){
        if (paymentRepository.existsById(id))
            paymentRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Payment with ID " + id + " doesn't exist");
    }

}