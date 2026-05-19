package org.project.expressart.Usuario.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService{
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepo){
        this.usuarioRepository = usuarioRepo;
    }

    public Page<Usuario> getAllProducts(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public void agregarCliente(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    // TODO: seguir editando
    public UsuarioResponseDTO getUsuario(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteUsuario(Long id) {usuarioRepository.deleteById(id);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioActualizado = usuarioRepository.findById(id).orElse(null);
        if (usuarioActualizado == null) {
            return null;
        }
        usuarioActualizado.setNombre_usuario(usuario.getNombre_usuario());
        usuarioActualizado.setEmail(usuario.getEmail());
        usuarioActualizado.setNombre(usuario.getNombre());
        usuarioActualizado.setContraseña(usuario.getContraseña());
        usuarioActualizado.setAvatar_url(usuario.getAvatar_url());
        usuarioActualizado.setBiografia(usuario.getBiografia());
        usuarioActualizado.setToken(usuario.getToken());
        return usuarioRepository.save(usuarioActualizado);
    }

}