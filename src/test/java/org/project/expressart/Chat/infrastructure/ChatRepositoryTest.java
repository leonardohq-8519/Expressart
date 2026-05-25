package org.project.expressart.Chat.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldReturnEmpty_WhenOrderIdDoesNotExist() {
        // Act
        Optional<Chat> result = chatRepository.findByOrderId(999L);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveAndFindChatByOrderId_WhenValidChatIsPersisted() {
        // 1. Crear y persistir el Usuario Artista
        Usuario artista = new Usuario();
        artista.setName("Artista Test");
        artista.setUsername("artista_test");
        artista.setEmail("artista@test.com");
        artista.setIsActive(true);
        artista.setIsVerified(true);
        Usuario artistaGuardado = entityManager.persistAndFlush(artista);

        // 2. Crear y persistir el Usuario Cliente
        Usuario cliente = new Usuario();
        cliente.setName("Cliente Test");
        cliente.setUsername("cliente_test");
        cliente.setEmail("cliente@test.com");
        cliente.setIsActive(true);
        cliente.setIsVerified(true);
        Usuario clienteGuardado = entityManager.persistAndFlush(cliente);

        // 3. Insertar Perfil de Artista vía SQL Nativo (evita problemas de getters/setters booleanos)
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO perfiles_artista (id, disponible, fecha_creacion, ordenes_completadas, stripe_onboarding, total_reviews, usuario_id) " +
                        "VALUES (1, true, NOW(), 0, false, 0, " + artistaGuardado.getId() + ")")
                .executeUpdate();

        // 4. Insertar la Comisión Padre requerida
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO comisiones (id, descripcion, esta_activa, fecha_creacion, titulo, perfil_artista_id) " +
                        "VALUES (1, 'Descripcion de la comision madre', true, NOW(), 'Comision Base Test', 1)")
                .executeUpdate();

        // 5. Insertar la Opción de Comisión (opciones_comision) vinculada a la comisión (id=1)
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO opciones_comision (id, descripcion, esta_activa, incluye_archivo_fuente, nombre, numero_revisiones, precio, tiempo_entrega, comision_id) " +
                        "VALUES (1, 'Descripcion de la opcion', true, true, 'Opcion Premium', 3, 100.00, 5, 1)")
                .executeUpdate();

        // 6. Insertar la Orden directamente en la tabla 'orders' con todas sus llaves foráneas obligatorias
        entityManager.getEntityManager()
                .createNativeQuery("INSERT INTO orders (id, descripcion_trabajo, estado, fecha_creacion, fecha_limite, precio_final, artista_id, cliente_id, opcion_comision_id) " +
                        "VALUES (1, 'Test ExpressArt', 'PENDIENTE', NOW(), NOW() + 7, 100.00, " + artistaGuardado.getId() + ", " + clienteGuardado.getId() + ", 1)")
                .executeUpdate();

        // 7. Crear el objeto Chat mapeando la orden con ID 1
        Orden ordenMock = new Orden();
        ordenMock.setId(1L);

        Chat chat = new Chat();
        chat.setOrden(ordenMock);
        chat.setCanalRedis("chat:1");
        chat.setFechaCreacion(ZonedDateTime.now());

        // Guardamos el chat
        entityManager.persistAndFlush(chat);

        // Limpiamos la caché de persistencia para forzar a Spring Data a buscar en la base de datos real
        entityManager.clear();

        // Act: Ejecutar el método del repositorio bajo prueba
        Optional<Chat> encontrado = chatRepository.findByOrderId(1L);

        // Assert: Validaciones
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCanalRedis()).isEqualTo("chat:1");
    }
}