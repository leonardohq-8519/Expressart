package org.project.expressart.ArchivoPost.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostUpdateDTO;
import org.project.expressart.ArchivoPost.infrastructure.ArchivoPostRepository;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArchivoPostServiceTest {

    @Mock
    private ArchivoPostRepository archivePostRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArchivePostService archivePostService;

    private ArchivoPost archivoPost;
    private ArchivoPostResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(archivePostService, "modelMapper", modelMapper);

        archivoPost = new ArchivoPost();
        archivoPost.setId(1L);
        archivoPost.setUrl("https://s3.amazonaws.com/bucket/file.pdf");
        archivoPost.setOrdenVisualizacion(1);

        responseDTO = new ArchivoPostResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/file.pdf");
    }

    @Test
    void shouldReturnArchivesWhenGetByPostAndExists() {
        when(archivePostRepository.findByPostId(1L)).thenReturn(List.of(archivoPost));
        when(modelMapper.map(archivoPost, ArchivoPostResponseDTO.class)).thenReturn(responseDTO);

        List<ArchivoPostResponseDTO> result = archivePostService.getByPost(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByPostAndNoArchivesExist() {
        when(archivePostRepository.findByPostId(99L)).thenReturn(List.of());

        assertThatThrownBy(() -> archivePostService.getByPost(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnArchiveWhenGetByIdAndExists() {
        when(archivePostRepository.findById(1L)).thenReturn(Optional.of(archivoPost));
        when(modelMapper.map(archivoPost, ArchivoPostResponseDTO.class)).thenReturn(responseDTO);

        ArchivoPostResponseDTO result = archivePostService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByIdAndNotExists() {
        when(archivePostRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> archivePostService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnUpdatedArchiveWhenUpdateAndExists() {
        ArchivoPostUpdateDTO updateDTO = new ArchivoPostUpdateDTO("https://new-url.com", 2);
        when(archivePostRepository.findById(1L)).thenReturn(Optional.of(archivoPost));
        when(archivePostRepository.save(any(ArchivoPost.class))).thenReturn(archivoPost);
        when(modelMapper.map(any(ArchivoPost.class), eq(ArchivoPostResponseDTO.class))).thenReturn(responseDTO);

        ArchivoPostResponseDTO result = archivePostService.update(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(archivePostRepository).save(any(ArchivoPost.class));
    }

    @Test
    void shouldDeleteArchiveWhenExistsById() {
        when(archivePostRepository.existsById(1L)).thenReturn(true);
        doNothing().when(archivePostRepository).deleteById(1L);

        archivePostService.delete(1L);

        verify(archivePostRepository).deleteById(1L);
    }
}
