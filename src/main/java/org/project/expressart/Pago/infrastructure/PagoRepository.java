package org.project.expressart.Pago.infrastructure;

import org.project.expressart.Pago.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByOrderId(Long orderId);

    Optional<Pago> findByStripePaymentIntentId(String stripePaymentIntentId);
}