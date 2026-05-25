
package org.project.expressart.CuentaOAuth.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.expressart.PerfilCliente.domain.PerfilCliente;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthCreateDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthResponseDTO;
import org.project.expressart.CuentaOAuth.application.dto.CuentaOAuthUpdateDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthAccountService {

    private record OAuthData(
            String providerId,
            String email,
            String name,
            String avatarUrl
    ){}

    private final UsuarioRepository userRepository;
    private final PerfilClienteRepository clientProfileRepository;

    @Transactional
    public Usuario processOAuthLogin(OAuth2User oAuth2User, String provider){
        OAuthData data = extractData(oAuth2User, provider);
        Optional<Usuario> userPerOAuth = userRepository.findByOAuthProveedorAndProveedorId(provider, data.providerId);

        if (userPerOAuth.isPresent()){
            return userPerOAuth.get();
        }

        Optional<Usuario> userPerEmail = userRepository.findByEmail(data.email());

        if (userPerEmail.isPresent()){
            return linkOAuth(userPerEmail.get(), data, provider);
        }

        return createOAuthUser(data, provider);
    }

    private Usuario createOAuthUser(OAuthData data, String provider){
        String username = generateUsername(data.email());
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setEmail(data.email());
        user.setName(data.name());
        user.setAvatar_url(data.avatarUrl());
        user.setPassword(null);
        user.setIsVerified(true);

        CuentaOAuth oAuthAccount = new CuentaOAuth();
        oAuthAccount.setUsuario(user);
        oAuthAccount.setProveedor(provider);
        oAuthAccount.setProveedorId(data.providerId());

        user.getOAuthAccounts().add(oAuthAccount);
        Usuario savedUser = userRepository.save(user);

        PerfilCliente clientProfile = new PerfilCliente();
        clientProfile.setUsuario(savedUser);
        clientProfileRepository.save(clientProfile);
        return savedUser;
    }

    private Usuario linkOAuth(Usuario user, OAuthData data, String provider){
        CuentaOAuth oAuthAccount = new CuentaOAuth();
        oAuthAccount.setUsuario(user);
        oAuthAccount.setProveedor(provider);
        oAuthAccount.setProveedorId(data.providerId());

        user.getOAuthAccounts().add(oAuthAccount);

        if (user.getAvatar_url() == null && data.avatarUrl() != null){
            user.setAvatar_url(data.avatarUrl());
        }

        return userRepository.save(user);
    }

    private OAuthData extractData(OAuth2User oAuth2User, String provider){
        return switch (provider){
            case "google" -> new OAuthData(
                    oAuth2User.getAttribute("sub"),
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("name"),
                    oAuth2User.getAttribute("picture")
            );
            case "discord" -> new OAuthData(
                    oAuth2User.getAttribute("id"),
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("username"),
                    oAuth2User.getAttribute("avatar")
            );
            default -> throw new IllegalArgumentException("OAuth provider not supported: " + provider);
        };
    }

    private String generateUsername(String email){
        String base = email.split("@")[0].toLowerCase().replaceAll("[^a-z0-9]", "");
        String suffix = UUID.randomUUID().toString().substring(0,4);
        String username = base + "_" + suffix;

        while (userRepository.existsByUsername(username)){
            suffix = UUID.randomUUID().toString().substring(0,4);
            username = base + "_" + suffix;
        }
        return username;
    }

    public List<CuentaOAuthResponseDTO> getByUser(Long userId) { return new ArrayList<>(); }
    public CuentaOAuthResponseDTO getById(Long id) { return new CuentaOAuthResponseDTO(); }
    public CuentaOAuthResponseDTO create(CuentaOAuthCreateDTO dto) { return new CuentaOAuthResponseDTO(); }
    public CuentaOAuthResponseDTO update(Long id, CuentaOAuthUpdateDTO dto) { return new CuentaOAuthResponseDTO(); }
    public void delete(Long id) {}
    public void deleteByUser(Long userId) {}
}