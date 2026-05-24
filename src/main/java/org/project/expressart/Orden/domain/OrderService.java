package org.project.expressart.Orden.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final OrdenRepository orderRepository;
    public List<OrderResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return orderRepository.findAllBy(pageable);
    }
    public OrderResponseDTO  findById (Long id){
        Orden order = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO findByClienteId (Long clientId){
        Orden order = orderRepository.findByClienteId(clientId).orElseThrow(()-> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO findByArtistaId (Long artistId){
        Orden order = orderRepository.findByArtistaId(artistId).orElseThrow(()-> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO findByClienteIdAndEstado (Long clientId, EstadoOrden status){
        Orden order = orderRepository.findByClienteIdAndEstado(clientId, status).orElseThrow(()-> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO findByArtistaIdAndEstado (Long artistId, EstadoOrden status){
        Orden order = orderRepository.findByArtistaIdAndEstado(artistId, status).orElseThrow(()-> new ResourceNotFoundEXception("Order not found"));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO create(OrderRequestDTO request){
    }
    public OrderResponseDTO  update (Long id, OrderRequestDTO request){
    }
    public OrderResponseDTO  updateEstado (Long id, EstadoOrden status){
    }
    public void delete (Long id){
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Order with ID " + id + " doesn't exist");
    }

}
