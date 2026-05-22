package org.project.expressart.Usuario.domain;


import jakarta.transaction.Transactional;
import org.project.expressart.Usuario.dto.UsuarioRequestDTO;
import org.project.expressart.Usuario.dto.UsuarioResponseDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private final UsuarioRepository userRepository;

    public UserService(UsuarioRepository userRepo){
        this.userRepository = userRepo;
    }

    public Page<UsuarioResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void createUser(UsuarioRequestDTO user){
        userRepository.save(user);
    }

    public UsuarioResponseDTO getUserById(Long id){
        Usuario user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("User not found"));
        UsuarioResponseDTO dto = modelMapper.map(user, UsuarioResponseDTO.class);
        return dto;
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, Usuario user) {
        Usuario updatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getUsername()!= null && !user.getEmail().isEmpty())
            updatedUser.setUsername(user.getUsername());

        if (user.getEmail() != null && !user.getEmail().isEmpty())
            updatedUser.setEmail(user.getEmail());

        if (user.getName() != null && !user.getName().isEmpty())
            updatedUser.setName(user.getName());

        if (user.getPassword() != null && !user.getPassword().isEmpty())
            updatedUser.setPassword(user.getPassword());

        updatedUser.setAvatar_url(user.getAvatar_url());
        updatedUser.setBiography(user.getBiography());
        updatedUser.setToken(user.getToken());
        userRepository.save(updatedUser);
    }
}