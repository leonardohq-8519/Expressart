package org.project.expressart.ImagenPost.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.domain.FileService;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostUpdateDTO;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostRepository;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PostPictureServiceTest {

    @Mock
    private ImagenPostRepository postPictureRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private FileService fileService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostPictureService postPictureService;

    private Post post;
    private ImagenPost imagenPost;
    private ImagenPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(postPictureService, "modelMapper", modelMapper);

        post = new Post();
        post.setId(1L);
        post.setImagenes(new ArrayList<>());

        imagenPost = new ImagenPost();
        imagenPost.setId(1L);
        imagenPost.setUrl("https://s3.amazonaws.com/bucket/post-img.jpg");
        imagenPost.setOrden(0);

        responseDTO = new ImagenPostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/post-img.jpg");
        responseDTO.setOrden(0);
    }

    @Test
    void shouldReturnImagesWhenGetByPostIdAndExists() {
        post.getImagenes().add(imagenPost);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(modelMapper.map(imagenPost, ImagenPostResponseDTO.class)).thenReturn(responseDTO);

        List<ImagenPostResponseDTO> result = postPictureService.getByPostId(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByPostIdAndPostNotExists() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postPictureService.getByPostId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnImageWhenGetByIdAndImageExists() {
        imagenPost.setId(5L);
        post.getImagenes().add(imagenPost);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(modelMapper.map(imagenPost, ImagenPostResponseDTO.class)).thenReturn(responseDTO);

        ImagenPostResponseDTO result = postPictureService.getById(1L, 5L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByIdAndImageNotExists() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        assertThatThrownBy(() -> postPictureService.getById(1L, 99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnUpdatedImageWhenUpdateAndExists() {
        ImagenPostUpdateDTO updateDTO = new ImagenPostUpdateDTO("https://new-url.jpg", 1);
        when(postPictureRepository.findById(1L)).thenReturn(Optional.of(imagenPost));
        when(postPictureRepository.save(any(ImagenPost.class))).thenReturn(imagenPost);
        when(modelMapper.map(any(ImagenPost.class), eq(ImagenPostResponseDTO.class))).thenReturn(responseDTO);

        ImagenPostResponseDTO result = postPictureService.update(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(postPictureRepository).save(any(ImagenPost.class));
    }

    @Test
    void shouldThrowResourceNotFoundWhenUpdateAndNotExists() {
        when(postPictureRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postPictureService.update(99L, new ImagenPostUpdateDTO()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
