package org.project.expressart.ImagenPost.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.ImagenPost.application.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.application.dto.ImagenPostUpdateDTO;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostRepository;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exception.ImageLimitException;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagenPostServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ImagenPostRepository imagenPostRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostPictureService postPictureService;

    private Post postPrueba;
    private ImagenPost imagenPrueba;
    private ImagenPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        postPrueba = new Post();
        postPrueba.setId(1L);
        postPrueba.setImagenes(new ArrayList<>());

        imagenPrueba = new ImagenPost();
        imagenPrueba.setId(10L);
        imagenPrueba.setPost(postPrueba);
        imagenPrueba.setUrl("/storage/posts/1/foto.jpg");
        imagenPrueba.setOrden(0);

        responseDTO = new ImagenPostResponseDTO();
        responseDTO.setId(10L);
        responseDTO.setUrl("/storage/posts/1/foto.jpg");
        responseDTO.setOrden(0);
    }

    @Test
    void shouldReturnOrderedImagesList_WhenGetByPostIdIsCalled() throws ResourceNotFoundException {
        postPrueba.getImagenes().add(imagenPrueba);
        when(postRepository.findById(1L)).thenReturn(Optional.of(postPrueba));
        when(modelMapper.map(any(ImagenPost.class), eq(ImagenPostResponseDTO.class))).thenReturn(responseDTO);

        List<ImagenPostResponseDTO> resultado = postPictureService.getByPostId(1L);

        assertThat(resultado).isNotEmpty().hasSize(1);
        assertThat(resultado.get(0).getUrl()).isEqualTo("/storage/posts/1/foto.jpg");
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowResourceNotFoundException_WhenPostDoesNotExistInGetByPostId() {

        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postPictureService.getByPostId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No post pictures found for post id: 1");
    }

    @Test
    void shouldSaveImageSuccessfully_WhenCreateIsCalledWithValidData() {

        MockMultipartFile archivoMock = new MockMultipartFile("file", "test.jpg", "image/jpeg", "datos".getBytes());
        when(postRepository.findById(1L)).thenReturn(Optional.of(postPrueba));
        when(modelMapper.map(any(ImagenPost.class), eq(ImagenPostResponseDTO.class))).thenReturn(responseDTO);

        ImagenPostResponseDTO resultado = postPictureService.create(1L, archivoMock);

        assertThat(resultado).isNotNull();
        verify(imagenPostRepository, times(1)).save(any(ImagenPost.class));
    }

    @Test
    void shouldThrowImageLimitException_WhenPostAlreadyHasFourImagesInCreate() {

        MockMultipartFile archivoMock = new MockMultipartFile("file", "test.jpg", "image/jpeg", "datos".getBytes());

        for (int i = 0; i < 4; i++) {
            postPrueba.getImagenes().add(new ImagenPost());
        }
        when(postRepository.findById(1L)).thenReturn(Optional.of(postPrueba));

        assertThatThrownBy(() -> postPictureService.create(1L, archivoMock))
                .isInstanceOf(ImageLimitException.class)
                .hasMessageContaining("A post cannot have more than 4 images");

        verify(imagenPostRepository, never()).save(any(ImagenPost.class));
    }

    @Test
    void shouldModifyImageData_WhenUpdateIsCalledWithValidDTO() throws ResourceNotFoundException {

        ImagenPostUpdateDTO updateDTO = new ImagenPostUpdateDTO();
        updateDTO.setUrl("/nuevo/path.jpg");
        updateDTO.setOrden(1);

        ImagenPostResponseDTO updatedResponse = new ImagenPostResponseDTO();
        updatedResponse.setId(10L);
        updatedResponse.setUrl("/nuevo/path.jpg");
        updatedResponse.setOrden(1);

        when(imagenPostRepository.findById(10L)).thenReturn(Optional.of(imagenPrueba));
        when(modelMapper.map(any(ImagenPost.class), eq(ImagenPostResponseDTO.class))).thenReturn(updatedResponse);

        ImagenPostResponseDTO resultado = postPictureService.update(10L, updateDTO);

        assertThat(resultado.getUrl()).isEqualTo("/nuevo/path.jpg");
        assertThat(resultado.getOrden()).isEqualTo(1);
        verify(imagenPostRepository, times(1)).save(imagenPrueba);
    }

    @Test
    void shouldRemoveImageAndReorderRemaining_WhenDeleteIsSuccessful() {
        postPrueba.getImagenes().add(imagenPrueba);
        when(postRepository.findById(1L)).thenReturn(Optional.of(postPrueba));

        postPictureService.delete(1L, 10L);

        assertThat(postPrueba.getImagenes()).isEmpty();
        verify(postRepository, times(1)).save(postPrueba);
    }
}