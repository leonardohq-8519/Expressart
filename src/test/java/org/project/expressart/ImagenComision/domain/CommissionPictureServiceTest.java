package org.project.expressart.ImagenComision.domain;

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
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionRepository;
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
class CommissionPictureServiceTest {

    @Mock
    private ImagenComisionRepository commissionPictureRepository;

    @Mock
    private ComisionRepository commissionRepository;

    @Mock
    private FileService fileService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommissionPictureService commissionPictureService;

    private Comision comision;
    private ImagenComision imagenComision;
    private ImagenComisionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(commissionPictureService, "modelMapper", modelMapper);

        comision = new Comision();
        comision.setId(1L);
        comision.setImagenes(new ArrayList<>());

        imagenComision = new ImagenComision();
        imagenComision.setId(1L);
        imagenComision.setUrl("https://s3.amazonaws.com/bucket/img.jpg");
        imagenComision.setOrden(0);

        responseDTO = new ImagenComisionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUrl("https://s3.amazonaws.com/bucket/img.jpg");
        responseDTO.setOrden(0);
    }

    @Test
    void shouldReturnImagesWhenGetByCommissionAndExists() {
        comision.getImagenes().add(imagenComision);
        when(commissionRepository.findById(1L)).thenReturn(Optional.of(comision));
        when(modelMapper.map(imagenComision, ImagenComisionResponseDTO.class)).thenReturn(responseDTO);

        List<ImagenComisionResponseDTO> result = commissionPictureService.getByCommission(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByCommissionAndCommissionNotExists() {
        when(commissionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commissionPictureService.getByCommission(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnImageWhenGetByIdAndImageExists() {
        imagenComision.setId(5L);
        comision.getImagenes().add(imagenComision);
        when(commissionRepository.findById(1L)).thenReturn(Optional.of(comision));
        when(modelMapper.map(imagenComision, ImagenComisionResponseDTO.class)).thenReturn(responseDTO);

        ImagenComisionResponseDTO result = commissionPictureService.getById(1L, 5L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowResourceNotFoundWhenGetByIdAndImageNotExists() {
        when(commissionRepository.findById(1L)).thenReturn(Optional.of(comision));

        assertThatThrownBy(() -> commissionPictureService.getById(1L, 99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldReturnUpdatedImageWhenUpdateAndExists() {
        ImagenComisionUpdateDTO updateDTO = new ImagenComisionUpdateDTO("https://new-url.jpg", 1);
        when(commissionPictureRepository.findById(1L)).thenReturn(Optional.of(imagenComision));
        when(commissionPictureRepository.save(any(ImagenComision.class))).thenReturn(imagenComision);
        when(modelMapper.map(any(ImagenComision.class), eq(ImagenComisionResponseDTO.class))).thenReturn(responseDTO);

        ImagenComisionResponseDTO result = commissionPictureService.update(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(commissionPictureRepository).save(any(ImagenComision.class));
    }

    @Test
    void shouldThrowResourceNotFoundWhenUpdateAndNotExists() {
        when(commissionPictureRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commissionPictureService.update(99L, new ImagenComisionUpdateDTO()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
