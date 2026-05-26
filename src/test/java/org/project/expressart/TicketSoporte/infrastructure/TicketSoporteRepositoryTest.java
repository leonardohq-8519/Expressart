package org.project.expressart.TicketSoporte.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.project.expressart.TicketSoporte.domain.TicketSoporte;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TicketSoporteRepositoryTest {

    @Autowired
    private TicketSoporteRepository ticketRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario savedUser;

    @BeforeEach
    void setUp() {
        Usuario user = new Usuario();
        user.setUsername("soporte_user");
        user.setEmail("soporte@test.com");
        user.setName("Soporte User");
        user.setPassword("123456");
        user.setRegisterDate(ZonedDateTime.now());
        user.setIsActive(true);
        user.setIsVerified(false);
        savedUser = usuarioRepository.save(user);
    }

    @Test
    void shouldPersistTicketWhenSave() {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setUser(savedUser);
        ticket.setSubject("Duda sobre cuenta");
        ticket.setDescription("No puedo cambiar mi contraseña");
        ticket.setStatus(EstadoTicket.values()[0]);
        ticket.setCategory(CategoriaTicket.values()[0]);

        TicketSoporte saved = ticketRepository.save(ticket);

        assertThat(saved.getId()).isNotNull();
        Optional<TicketSoporte> found = ticketRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getSubject()).isEqualTo("Duda sobre cuenta");
    }

    @Test
    void shouldReturnEmptyWhenFindByIdAndNotExists() {
        Optional<TicketSoporte> result = ticketRepository.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnTicketsWhenFindByStatusAndCategory() {
        EstadoTicket estado = EstadoTicket.values()[0];
        CategoriaTicket categoria = CategoriaTicket.values()[0];

        TicketSoporte ticket = new TicketSoporte();
        ticket.setUser(savedUser);
        ticket.setSubject("Error en pasarela");
        ticket.setDescription("No procesa el pago correctamente");
        ticket.setStatus(estado);
        ticket.setCategory(categoria);
        ticketRepository.save(ticket);

        List<TicketSoporte> results = ticketRepository.findByStatusAndCategory(estado, categoria);

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getSubject()).isEqualTo("Error en pasarela");
    }

    @Test
    void shouldReturnEmptyListWhenFindByStatusAndNoTicketsExist() {
        List<TicketSoporte> results = ticketRepository.findByStatus(EstadoTicket.values()[0]);
        assertThat(results).isEmpty();
    }
}
