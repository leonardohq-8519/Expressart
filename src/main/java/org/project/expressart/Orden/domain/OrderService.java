package org.project.expressart.Orden.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final OrdenRepository orderRepository;

    public List<OrderResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Orden> ordenes = orderRepository.findAll(pageable).getContent();
        return convertToDtoList(ordenes);
    }

    public OrderResponseDTO findById(Long id) {
        Orden order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    public List<OrderResponseDTO> findByClienteId(Long clientId) {
        List<Orden> ordenes = orderRepository.findByClienteId(clientId);
        return convertToDtoList(ordenes);
    }

    public List<OrderResponseDTO> findByArtistaId(Long artistId) {
        List<Orden> ordenes = orderRepository.findByArtistaId(artistId);
        return convertToDtoList(ordenes);
    }

    public List<OrderResponseDTO> findByClienteIdAndEstado(Long clientId, EstadoOrden status) {
        List<Orden> ordenes = orderRepository.findByClienteIdAndEstado(clientId, status);
        return convertToDtoList(ordenes);
    }

    public List<OrderResponseDTO> findByArtistaIdAndEstado(Long artistId, EstadoOrden status) {
        List<Orden> ordenes = orderRepository.findByArtistaIdAndEstado(artistId, status);
        return convertToDtoList(ordenes);
    }

    public OrderResponseDTO create(OrderRequestDTO request) {
        Orden order = modelMapper.map(request, Orden.class);
        Orden savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponseDTO.class);
    }

    public OrderResponseDTO update(Long id, OrderRequestDTO request) {
        Orden existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Order not found"));

        modelMapper.map(request, existingOrder);
        existingOrder.setId(id);

        Orden updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderResponseDTO.class);
    }

    public OrderResponseDTO updateEstado(Long id, EstadoOrden status) {
        Orden existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Order not found"));

        existingOrder.setEstado(status);

        Orden updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderResponseDTO.class);
    }

    public void delete(Long id) {
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Order with ID " + id + " doesn't exist");
    }

    private List<OrderResponseDTO> convertToDtoList(List<Orden> ordenes) {
        return ordenes.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }
}