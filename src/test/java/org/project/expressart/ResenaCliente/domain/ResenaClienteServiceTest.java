package org.project.expressart.ResenaCliente.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.project.expressart.ResenaCliente.infrastructure.ResenaClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaClienteServiceTest {

    @Mock
    private ResenaClienteRepository cliReviewRepository;
    @Mock
    private OrdenRepository orderRepository;
    @Mock
    private UsuarioRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientReviewService clientReviewService;

    private ResenaCliente resena;
    private ClientReviewResponseDTO responseDTO;
    private ClientReviewRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        resena = new ResenaCliente();
        resena.setId(1L);
        resena.setPuntuacion((short) 5);
        resena.setComentario("Genial");

        responseDTO = new ClientReviewResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPuntuacion((short) 5);
        responseDTO.setComentario("Genial");

        requestDTO = new ClientReviewRequestDTO();
        requestDTO.setOrdenId(1L);
        requestDTO.setClienteId(1L);
        requestDTO.setArtistaId(2L);
        requestDTO.setPuntuacion((short) 5);
        requestDTO.setComentario("Genial");
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(cliReviewRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<ClientReviewResponseDTO> resultado = clientReviewService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(cliReviewRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarResena_cuandoExiste() throws Exception {
        when(cliReviewRepository.findById(1L)).thenReturn(Optional.of(resena));
        when(modelMapper.map(resena, ClientReviewResponseDTO.class)).thenReturn(responseDTO);

        ClientReviewResponseDTO resultado = clientReviewService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarYRetornarResena() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Orden()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new Usuario()));
        when(cliReviewRepository.save(any(ResenaCliente.class))).thenReturn(resena);
        when(modelMapper.map(any(ResenaCliente.class), eq(ClientReviewResponseDTO.class))).thenReturn(responseDTO);

        ClientReviewResponseDTO resultado = clientReviewService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(cliReviewRepository, times(1)).save(any(ResenaCliente.class));
    }
}