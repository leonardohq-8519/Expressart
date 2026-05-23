package org.project.expressart.Usuario.domain;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.project.expressart.Usuario.dto.UserRequestDTO;
import org.project.expressart.Usuario.dto.UserResponseDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserService(UsuarioRepository userRepo){
        this.userRepository = userRepo;
    }

    public List<UserResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findAllBy(pageable);
    }

    public UserResponseDTO create(UserRequestDTO userdto) throws BadRequestException {
        if (userRepository.existsByEmail(userdto.getEmail()))
            throw new BadRequestException("El email ya está en uso");
        if (userRepository.existsByUsername(userdto.getUsername()))
            throw new BadRequestException("El username ya está en uso");
        Usuario user = new Usuario();
        user.setUsername(userdto.getUsername());
        user.setEmail(userdto.getEmail());
        user.setName(userdto.getName());
        user.setPassword(userdto.getPassword());
        user.setAvatar_url(userdto.getAvatar_url());
        user.setBiography(userdto.getBiography());
        user.setIsActive(true);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }


    public UserResponseDTO findById(Long id){
        Usuario user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO findByEmail(String email){
        Usuario user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundEXception("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> findSeguidoresByArtistaId(Long id) {
        List<Usuario> seguidores = userRepository.findSeguidoresByArtistaId(id);
        return seguidores.stream()
                .map(seguidor -> modelMapper.map(seguidor, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
         else
            throw new EntityNotFoundException("User with ID " + id + " doesn't exist");
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO request) {
        Usuario updatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getUsername()!= null && !request.getEmail().isEmpty())
            updatedUser.setUsername(request.getUsername());

        if (request.getEmail() != null && !request.getEmail().isEmpty())
            updatedUser.setEmail(request.getEmail());

        if (request.getName() != null && !request.getName().isEmpty())
            updatedUser.setName(request.getName());

        if (request.getPassword() != null && !request.getPassword().isEmpty())
            updatedUser.setPassword(request.getPassword());
        updatedUser.setAvatar_url(request.getAvatar_url());
        updatedUser.setBiography(request.getBiography());
        userRepository.save(updatedUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }
}