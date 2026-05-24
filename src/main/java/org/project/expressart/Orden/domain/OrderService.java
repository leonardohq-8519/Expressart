package org.project.expressart.Orden.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdenRepository orderRepository;
    public List<OrderResponseDTO> findAll(){
    }
    public OrderResponseDTO  findById (Long id){
    }
    public OrderResponseDTO findByClienteId (Long clientId){
    }
    public OrderResponseDTO findByArtistaId (Long artistId){
    }
    public OrderResponseDTO findByClienteIdAndEstado (Long clientId, EstadoOrden status){
    }
    public OrderResponseDTO findByArtistaIdAndEstado (Long artistId, EstadoOrden status){
    }
    public OrderResponseDTO create(OrderRequestDTO request){
    }
    public OrderResponseDTO  update (Long id, OrderRequestDTO request){
    }
    public OrderResponseDTO  updateEstado (Long id, EstadoOrden status){
    }
    public void delete (Long id){
    }

}
