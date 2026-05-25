package org.project.expressart.Devolucion.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.infrastructure.DevolucionRepository;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DevolucionServiceTest {

    @Mock
    private DevolucionRepository devolutionRepository;

    @Mock
    private OrdenRepository orderRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private DevolutionService devolutionService;

    private Devolucion devolucion;
    private DevolutionRequestDTO requestDTO;
    private DevolutionResponseDTO responseDTO;
    private Orden orden;

    @BeforeEach
    void setUp() {
        devolutionService = new DevolutionService(devolutionRepository, orderRepository);
        ReflectionTestUtils.setField(devolutionService, "modelMapper", modelMapper);

        orden = new Orden();
        orden.setId(1L);

        devolucion = new Devolucion();
        devolucion.setId(1L);
        devolucion.setOrder(orden);
        devolucion.setMotivo("Producto defectuoso");
        devolucion.setMontoReembolso(new BigDecimal("100.00"));
        devolucion.setEstado(EstadoDevolucion.SOLICITADA);

        requestDTO = new DevolutionRequestDTO();
        requestDTO.setOrdenId(1L);
        requestDTO.setMotivo("Producto defectuoso");
        requestDTO.setMontoReembolso(new BigDecimal("100.00"));

        responseDTO = new DevolutionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEstado(EstadoDevolucion.SOLICITADA);
    }

    @Test
    @DisplayName("Debería retornar lista de devoluciones al llamar findAll")
    void shouldReturnDevolutionListWhenFindAll() {
        when(devolutionRepository.findAllBy(any())).thenReturn(List.of(devolucion));

        List<DevolutionResponseDTO> result = devolutionService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(devolutionRepository, times(1)).findAllBy(any());
    }

    @Test
    @DisplayName("Debería retornar devolución cuando existe el ID")
    void shouldReturnDevolutionWhenFindByIdExists() throws ResourceNotFoundException {
        when(devolutionRepository.findById(1L)).thenReturn(Optional.of(devolucion));

        DevolutionResponseDTO result = devolutionService.findById(1L);

        assertNotNull(result);
        verify(devolutionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la devolución no existe por ID")
    void shouldThrowExceptionWhenDevolutionNotFoundById() {
        when(devolutionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> devolutionService.findById(99L));
    }

    @Test
    @DisplayName("Debería retornar devolución cuando existe por orden ID")
    void shouldReturnDevolutionWhenFindByOrderIdExists() throws ResourceNotFoundException {
        when(devolutionRepository.findByOrderId(1L)).thenReturn(Optional.of(devolucion));

        DevolutionResponseDTO result = devolutionService.findByOrderId(1L);

        assertNotNull(result);
        verify(devolutionRepository, times(1)).findByOrderId(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no existe devolución para la orden")
    void shouldThrowExceptionWhenDevolutionNotFoundByOrderId() {
        when(devolutionRepository.findByOrderId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> devolutionService.findByOrderId(99L));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no hay devoluciones por estado")
    void shouldThrowExceptionWhenNoDevolutionsFoundByEstado() {
        when(devolutionRepository.findByEstado(EstadoDevolucion.APROBADA)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> devolutionService.findByEstado(EstadoDevolucion.APROBADA));
    }

    @Test
    @DisplayName("Debería crear devolución cuando la orden existe")
    void shouldCreateDevolutionWhenOrdenExists() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(devolutionRepository.save(any(Devolucion.class))).thenReturn(devolucion);

        DevolutionResponseDTO result = devolutionService.create(requestDTO);

        assertNotNull(result);
        verify(devolutionRepository, times(1)).save(any(Devolucion.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al crear devolución cuando la orden no existe")
    void shouldThrowExceptionWhenCreatingDevolutionWithNonExistentOrden() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> devolutionService.create(requestDTO));
        verify(devolutionRepository, never()).save(any(Devolucion.class));
    }

    @Test
    @DisplayName("Debería actualizar el estado de la devolución cuando existe")
    void shouldUpdateStatusWhenDevolutionExists() throws ResourceNotFoundException {
        when(devolutionRepository.findById(1L)).thenReturn(Optional.of(devolucion));
        when(devolutionRepository.save(any(Devolucion.class))).thenReturn(devolucion);

        DevolutionResponseDTO result = devolutionService.updateStatus(1L, EstadoDevolucion.APROBADA);

        assertNotNull(result);
        verify(devolutionRepository, times(1)).save(any(Devolucion.class));
    }

    @Test
    @DisplayName("Debería eliminar devolución cuando existe el ID")
    void shouldDeleteDevolutionWhenExists() {
        when(devolutionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(devolutionRepository).deleteById(1L);

        devolutionService.delete(1L);

        verify(devolutionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción al eliminar devolución cuando no existe")
    void shouldThrowExceptionWhenDeletingNonExistentDevolution() {
        when(devolutionRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> devolutionService.delete(99L));
        verify(devolutionRepository, never()).deleteById(anyLong());
    }
}