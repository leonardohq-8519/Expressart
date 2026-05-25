package org.project.expressart.PerfilCliente.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilClienteServiceTest {

    @Mock
    private PerfilClienteRepository clientProfileRepository;

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientProfileService clientProfileService;

    private PerfilCliente perfilCliente;
    private ClientProfileResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        perfilCliente = new PerfilCliente();
        perfilCliente.setId(1L);

        responseDTO = new ClientProfileResponseDTO();
        responseDTO.setId(1L);
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(clientProfileRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<ClientProfileResponseDTO> resultado = clientProfileService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(clientProfileRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarPerfil_cuandoExiste() throws Exception {
        when(clientProfileRepository.findById(1L)).thenReturn(Optional.of(perfilCliente));
        when(modelMapper.map(perfilCliente, ClientProfileResponseDTO.class)).thenReturn(responseDTO);

        ClientProfileResponseDTO resultado = clientProfileService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarPerfilExitosamente() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(clientProfileRepository.save(any(PerfilCliente.class))).thenReturn(perfilCliente);
        when(modelMapper.map(any(PerfilCliente.class), eq(ClientProfileResponseDTO.class))).thenReturn(responseDTO);

        ClientProfileResponseDTO resultado = clientProfileService.create(1L);

        assertThat(resultado).isNotNull();
        verify(clientProfileRepository, times(1)).save(any(PerfilCliente.class));
    }
}