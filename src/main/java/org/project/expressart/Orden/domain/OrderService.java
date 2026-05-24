package org.project.expressart.Orden.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
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
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private final OpcionesComisionRepository commissionOptionsRepository;
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
        Orden order = new Orden();
        Usuario artista = userRepository.findById(request.getArtistaId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        order.setArtista(artista);
        Usuario client = userRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        order.setCliente(client);
        OpcionesComision commOption = commissionOptionsRepository.findById(request.getOpcionComisionId())
                .orElseThrow(() -> new EntityNotFoundException("Commission option not found"));
        order.setOpcionComision(commOption);
        order.setDescripcionTrabajo(request.getDescripcionTrabajo());
        order.setPrecioFinal(request.getPrecioFinal());
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDTO.class);
    }
    public OrderResponseDTO  update (Long id, OrderRequestDTO request){
        Orden updatedOrder = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));;
        Usuario artista = userRepository.findById(request.getArtistaId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        updatedOrder.setArtista(artista);
        Usuario client = userRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        updatedOrder.setCliente(client);
        OpcionesComision commOption = commissionOptionsRepository.findById(request.getOpcionComisionId())
                .orElseThrow(() -> new EntityNotFoundException("Commission option not found"));
        updatedOrder.setOpcionComision(commOption);
        updatedOrder.setDescripcionTrabajo(request.getDescripcionTrabajo());
        updatedOrder.setPrecioFinal(request.getPrecioFinal());
        orderRepository.save(updatedOrder);
        return modelMapper.map(updatedOrder, OrderResponseDTO.class);
    }
    public OrderResponseDTO  updateEstado (Long id, EstadoOrden status){
        Orden updatedOrder = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        updatedOrder.setEstado(status);
        orderRepository.save(updatedOrder);
        return modelMapper.map(updatedOrder, OrderResponseDTO.class);
    }
    public void delete (Long id){
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Order with ID " + id + " doesn't exist");
    }

}
