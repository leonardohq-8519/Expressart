package org.project.expressart.ImagenComision.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagenComisionServiceTest {

    @Mock
    private ImagenComisionRepository commissionPictureRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private CommissionPictureService commissionPictureService;

    private ImagenComision imagenComision;

    @BeforeEach
    void setUp() {
        commissionPictureService = new CommissionPictureService(commissionPictureRepository);
        ReflectionTestUtils.setField(commissionPictureService, "modelMapper", modelMapper);

        imagenComision = new ImagenComision();
        imagenComision.setId(1L);
        imagenComision.setUrl("http://example.com/imagen.jpg");
        imagenComision.setOrden(1);
    }

    @Test
    @DisplayName("Debería guardar imagen de comisión correctamente")
    void shouldSaveImagenComisionWhenValidData() {
        when(commissionPictureRepository.save(any(ImagenComision.class))).thenReturn(imagenComision);

        ImagenComision result = commissionPictureRepository.save(imagenComision);

        assertNotNull(result);
        assertEquals("http://example.com/imagen.jpg", result.getUrl());
        verify(commissionPictureRepository, times(1)).save(any(ImagenComision.class));
    }

    @Test
    @DisplayName("Debería retornar vacío cuando la imagen no existe por ID")
    void shouldReturnEmptyWhenImagenComisionNotFoundById() {
        when(commissionPictureRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ImagenComision> result = commissionPictureRepository.findById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debería retornar imagen cuando existe por ID")
    void shouldReturnImagenComisionWhenFoundById() {
        when(commissionPictureRepository.findById(1L)).thenReturn(Optional.of(imagenComision));

        Optional<ImagenComision> result = commissionPictureRepository.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUrl()).isEqualTo("http://example.com/imagen.jpg");
    }

    @Test
    @DisplayName("Debería retornar lista de imágenes")
    void shouldReturnImagenComisionListWhenFindAll() {
        when(commissionPictureRepository.findAll()).thenReturn(List.of(imagenComision));

        List<ImagenComision> result = commissionPictureRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería eliminar imagen de comisión cuando existe")
    void shouldDeleteImagenComisionWhenExists() {
        doNothing().when(commissionPictureRepository).deleteById(1L);

        commissionPictureRepository.deleteById(1L);

        verify(commissionPictureRepository, times(1)).deleteById(1L);
    }
}