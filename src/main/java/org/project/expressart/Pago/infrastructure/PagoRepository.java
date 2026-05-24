package org.project.expressart.Pago.infrastructure;

import org.project.expressart.Pago.domain.Pago;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<PaymentResponseDTO> findAllBy(Pageable pageable);
    Optional<Pago> findByOrderId(Long orderId);

    Optional<Pago> findByStripePaymentIntentId(String stripePaymentIntentId);
}