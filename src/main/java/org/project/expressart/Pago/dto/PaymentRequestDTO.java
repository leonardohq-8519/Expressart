package org.project.expressart.Pago.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDTO {
    private Long ordenId;
    private BigDecimal monto;
    private BigDecimal montoArtista;
    private BigDecimal montoComisionPlataforma;
    private String stripePaymentIntentId;
}