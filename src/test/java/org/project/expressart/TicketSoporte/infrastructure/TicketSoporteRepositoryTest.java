package org.project.expressart.TicketSoporte.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TicketSoporteRepositoryTest {

    @Autowired
    private TicketSoporteRepository ticketRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_debePersistirTicketCorrectamente() {

        Usuario dummyUser = new Usuario();
        dummyUser.setUsername("soporte_user");
        dummyUser.setEmail("soporte@test.com");
        dummyUser.setName("Soporte User");
        dummyUser.setPassword("123456");
        dummyUser = entityManager.persistAndFlush(dummyUser);

        TicketSoporte nuevoTicket = new TicketSoporte();
        nuevoTicket.setUser(dummyUser);
        nuevoTicket.setSubject("Duda sobre cuenta");
        nuevoTicket.setDescription("No puedo cambiar mi contraseña");

        nuevoTicket.setStatus(EstadoTicket.values()[0]);
        nuevoTicket.setCategory(CategoriaTicket.values()[0]);

        TicketSoporte guardado = ticketRepository.save(nuevoTicket);

        assertThat(guardado.getId()).isNotNull();

        Optional<TicketSoporte> encontrado = ticketRepository.findById(guardado.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getSubject()).isEqualTo("Duda sobre cuenta");
    }

    @Test
    void findByStatusAndCategory_debeRetornarTicketsFiltrados() {

        Usuario dummyUser = new Usuario();
        dummyUser.setUsername("soporte_user_2");
        dummyUser.setEmail("soporte2@test.com");
        dummyUser.setName("Soporte User 2");
        dummyUser.setPassword("123456");
        dummyUser = entityManager.persistAndFlush(dummyUser);

        EstadoTicket estadoBuscado = EstadoTicket.values()[0];
        CategoriaTicket categoriaBuscada = CategoriaTicket.values()[0];

        TicketSoporte ticket = new TicketSoporte();
        ticket.setUser(dummyUser);
        ticket.setSubject("Error en pasarela");
        ticket.setDescription("No procesa el pago correctamente");
        ticket.setStatus(estadoBuscado);
        ticket.setCategory(categoriaBuscada);
        entityManager.persistAndFlush(ticket);

        List<TicketSoporte> resultados = ticketRepository.findByStatusAndCategory(estadoBuscado, categoriaBuscada);

        assertThat(resultados).isNotEmpty();
        assertThat(resultados.get(0).getSubject()).isEqualTo("Error en pasarela");
    }
}