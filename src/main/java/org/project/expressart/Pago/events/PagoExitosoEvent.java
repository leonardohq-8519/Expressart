package org.project.expressart.Pago.events;

import java.math.BigDecimal;

public record PagoExitosoEvent(Long pagoId, Long ordenId, Long clienteId, String clienteEmail, BigDecimal monto) {}