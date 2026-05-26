package org.project.expressart.Pago.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Pago.domain.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    void findByOrderId_debeRetornarVacio_cuandoNoExiste() {
        Optional<Pago> resultado = pagoRepository.findByOrderId(999L);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByStripePaymentIntentId_debeRetornarVacio_cuandoNoExiste() {
        Optional<Pago> resultado = pagoRepository.findByStripePaymentIntentId("non-existent-intent");
        assertThat(resultado).isEmpty();
    }
}