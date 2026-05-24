package org.project.expressart.RedSocialArtista.infrastructure;

import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RedSocialArtistaRepository extends JpaRepository<RedSocialArtista, Long> {

    List<RedSocialArtista> findByPerfilArtistaId(Long perfilArtistaId);

    Optional<RedSocialArtista> findByPerfilArtistaIdAndPlataforma(Long perfilArtistaId, String plataforma);

    void deleteByPerfilArtistaId(Long perfilArtistaId);
}