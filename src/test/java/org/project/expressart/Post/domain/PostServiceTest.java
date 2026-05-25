package org.project.expressart.Post.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PortafolioRepository portafolioRepository;
    @Mock
    private CategoriaRepository categoryRepository;
    @Mock
    private TagsRepository tagsRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostResponseDTO responseDTO;
    private PostRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitulo("Obra Arte");

        responseDTO = new PostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Obra Arte");

        requestDTO = new PostRequestDTO();
        requestDTO.setPortafolioId(1L);
        requestDTO.setTitulo("Obra Arte");
        requestDTO.setCategoriaIds(List.of());
        requestDTO.setTagIds(List.of());
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(postRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<PostResponseDTO> resultado = postService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(postRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarPost_cuandoExiste() throws Exception {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostResponseDTO.class)).thenReturn(responseDTO);

        PostResponseDTO resultado = postService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarCorrectamente() {
        when(portafolioRepository.findById(1L)).thenReturn(Optional.of(new Portafolio()));
        when(categoryRepository.findAllById(anyList())).thenReturn(List.of());
        when(tagsRepository.findAllById(anyList())).thenReturn(List.of());
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(modelMapper.map(any(Post.class), eq(PostResponseDTO.class))).thenReturn(responseDTO);

        PostResponseDTO resultado = postService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(postRepository, times(1)).save(any(Post.class));
    }
}