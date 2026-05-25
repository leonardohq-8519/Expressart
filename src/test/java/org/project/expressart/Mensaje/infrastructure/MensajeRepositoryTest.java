package org.project.expressart.Mensaje.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Mensaje.domain.Mensaje;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOCAL_CASE=TRUE;DEFAULT_NULL_ORDER=HIGH;SET REFERENTIAL_INTEGRITY FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class MensajeRepositoryTest {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        mensajeRepository.deleteAll();
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE chats").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE orders").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE opciones_comision").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE usuario").executeUpdate();

        entityManager.getEntityManager().createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findAllBy_DebeRetornarListaVaciaCuandoNoHayMensajes() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Mensaje> result = mensajeRepository.findAllBy(pageable);
        assertThat(result).isEmpty();
    }

    @Test
    void findByChatId_DebeRetornarMensajesDelChat() {
        entityManager.getEntityManager().createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        Usuario usuario = new Usuario();
        usuario.setUsername("remitenteTest");
        usuario.setEmail("remitente@test.com");
        usuario.setName("Remitente");
        usuario.setPassword("password123");
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        usuario = entityManager.persistAndFlush(usuario);

        entityManager.getEntityManager().createNativeQuery(
                "INSERT INTO opciones_comision (comision_id, descripcion, esta_activa, incluye_archivo_fuente, nombre, numero_revisiones, precio, tiempo_entrega) " +
                        "VALUES (1, 'Descripción de prueba', true, false, 'Opción Base', 3, 50.00, 5)"
        ).executeUpdate();

        OpcionesComision opcion = entityManager.getEntityManager()
                .createQuery("SELECT o FROM OpcionesComision o", OpcionesComision.class)
                .setMaxResults(1)
                .getSingleResult();

        entityManager.getEntityManager().createNativeQuery(
                "INSERT INTO orders (precio_final, artista_id, cliente_id, fecha_creacion, fecha_limite, opcion_comision_id, descripcion_trabajo, estado) " +
                        "VALUES (50.00, 1, 1, NOW(), NOW(), ?, 'Trabajo de prueba', 'PENDIENTE')"
        ).setParameter(1, opcion.getId()).executeUpdate();

        Orden orden = entityManager.getEntityManager()
                .createQuery("SELECT o FROM Orden o", Orden.class)
                .setMaxResults(1)
                .getSingleResult();

        Chat chat = new Chat();
        chat.setCanalRedis("canal-test-123");
        chat.setFechaCreacion(ZonedDateTime.now());
        chat.setOrden(orden);
        chat = entityManager.persistAndFlush(chat);

        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setRemitente(usuario);
        mensaje.setTexto("Hola");
        mensaje.setFechaEnvio(ZonedDateTime.now());
        mensaje.setLeido(false);
        mensaje.setPersistido(false);
        entityManager.persistAndFlush(mensaje);

        entityManager.getEntityManager().createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();


        List<Mensaje> result = mensajeRepository.findByChatId(chat.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTexto()).isEqualTo("Hola");
    }
}