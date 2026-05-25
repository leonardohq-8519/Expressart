package org.project.expressart.CuentaOAuth.domain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.config.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final OAuthAccountService oAuthAccountService;

    @Value("${oauth2.redirect-url")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        Usuario user = oAuthAccountService.processOAuthLogin(oAuth2User, provider);

        //TODO: Adaptar la parte de JWT para generar los tokens respecto a la entidad y no al username
        String jwt = jwtService.generateToken(user);

        String destinyUrl = UriComponentsBuilder.fromUriString(redirectUrl).queryParam("token", jwt).build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, destinyUrl);
    }
}
