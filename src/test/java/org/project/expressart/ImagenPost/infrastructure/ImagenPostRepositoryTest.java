package org.project.expressart.ImagenPost.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER_CASE=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true"
})
class ImagenPostRepositoryTest {

    @Autowired
    private ImagenPostRepository imagenPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Post postBase;

    @BeforeEach
    void setUp() {

        imagenPostRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setIsActive(true);
        usuario.setIsVerified(true);
        usuario = entityManager.persistAndFlush(usuario);

        PerfilArtista perfilArtista = new PerfilArtista();
        perfilArtista.setUsuario(usuario);
        perfilArtista = entityManager.persistAndFlush(perfilArtista);

        Portafolio portafolio = new Portafolio();
        portafolio.setPerfilArtista(perfilArtista);
        portafolio.setTitulo("Portafolio de Prueba");
        portafolio.setEsPublico(true);
        portafolio = entityManager.persistAndFlush(portafolio);

        postBase = new Post();
        postBase.setTitulo("Post Base Test");
        postBase.setEsPublico(true);
        postBase.setFechaPublicacion(ZonedDateTime.now());
        postBase.setPortafolio(portafolio);

        postBase = entityManager.persistAndFlush(postBase);
    }

    @Test
    void findByPostIdOrderByOrdenAsc_DebeDevolverLasImagenesEnElOrdenCorrecto() {
        // Arrange
        ImagenPost segundaImagen = new ImagenPost();
        segundaImagen.setPost(postBase);
        segundaImagen.setUrl("url_segunda.jpg");
        segundaImagen.setOrden(1);

        ImagenPost primeraImagen = new ImagenPost();
        primeraImagen.setPost(postBase);
        primeraImagen.setUrl("url_primera.jpg");
        primeraImagen.setOrden(0);

        entityManager.persistAndFlush(segundaImagen);
        entityManager.persistAndFlush(primeraImagen);

        List<ImagenPost> resultado = imagenPostRepository.findByPostIdOrderByOrdenAsc(postBase.getId());

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getUrl()).isEqualTo("url_primera.jpg");
        assertThat(resultado.get(1).getUrl()).isEqualTo("url_segunda.jpg");
    }

    @Test
    void countByPostId_DebeRetornarCeroCuandoNoHayImagenes() {

        long conteo = imagenPostRepository.countByPostId(postBase.getId());

        assertThat(conteo).isZero();
    }
}