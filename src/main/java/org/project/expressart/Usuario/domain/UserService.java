package org.project.expressart.Usuario.domain;


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

    public void createUser(Usuario user){
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

    public Usuario updateUser(Long id, Usuario user) {
        Usuario updatedUser = userRepository.findById(id).orElse(null);
        if (updatedUser == null) {
            return null;
        }
        updatedUser.setNombre_usuario(user.getNombre_usuario());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setNombre(user.getNombre());
        updatedUser.setContraseña(user.getContraseña());
        updatedUser.setAvatar_url(user.getAvatar_url());
        updatedUser.setBiografia(user.getBiografia());
        updatedUser.setToken(user.getToken());
        return userRepository.save(updatedUser);
    }

}