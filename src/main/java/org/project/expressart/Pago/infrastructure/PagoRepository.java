package org.project.expressart.Pago.infrastructure;

import org.project.expressart.Pago.domain.Pago;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<PaymentResponseDTO> findAllBy(Pageable pageable);

    @Query("SELECT p FROM Pago p WHERE p.orden.id = :orderId")
    Optional<Pago> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT p FROM Pago p WHERE p.stripePaymentIntentId = :stripePaymentIntentId")
    Optional<Pago> findByStripePaymentIntentId(@Param("stripePaymentIntentId") String stripePaymentIntentId);
}