package org.project.expressart.Pago.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.infrastructure.PagoRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
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
    private final OrdenRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<PaymentResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return paymentRepository.findAllBy(pageable);
    }
    public PaymentResponseDTO findById (Long id)throws ResourceNotFoundException{
        Pago payment = paymentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public PaymentResponseDTO findByOrderId (Long orderId)throws ResourceNotFoundException{
        Pago payment = paymentRepository.findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public PaymentResponseDTO findByStripePaymentIntentId (String stripePaymentIntentId)throws ResourceNotFoundException{
        Pago payment = paymentRepository.findByStripePaymentIntentId(stripePaymentIntentId).orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }

    public PaymentResponseDTO create(PaymentRequestDTO request){
        Pago payment = new Pago();
        Orden order = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        payment.setOrden(order);
        payment.setMonto(request.getMonto());
        payment.setMontoArtista(request.getMontoArtista());
        payment.setMontoComisionPlataforma(request.getMontoComisionPlataforma());
        payment.setStripePaymentIntentId(request.getStripePaymentIntentId());
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentResponseDTO.class);

    }
    public PaymentResponseDTO updateStatus(Long id, EstadoPago status) throws ResourceNotFoundException {
        Pago updatedPayment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        updatedPayment.setEstado(status);
        paymentRepository.save(updatedPayment);
        return modelMapper.map(updatedPayment, PaymentResponseDTO.class);
    }
    public void delete (Long id){
        if (paymentRepository.existsById(id))
            paymentRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Payment with ID " + id + " doesn't exist");
    }

}