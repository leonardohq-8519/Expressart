package org.project.expressart.Post.infrastructure;

import org.junit.jupiter.api.Test;
import org.project.expressart.Post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void findByPortafolioId_debeRetornarVacio_cuandoNoExiste() {
        List<Post> resultado = postRepository.findByPortafolioId(999L);
        assertThat(resultado).isEmpty();
    }
}