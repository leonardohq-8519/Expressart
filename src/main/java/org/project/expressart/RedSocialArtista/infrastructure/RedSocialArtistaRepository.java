package org.project.expressart.RedSocialArtista.infrastructure;

import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedSocialArtistaRepository extends JpaRepository<RedSocialArtista, Long> {

    List<RedSocialArtista> findByPerfilArtistaId(Long perfilArtistaId);

    void deleteByPerfilArtistaId(Long perfilArtistaId);
    Boolean existsByPerfilArtista(Long perfilArtistaId);
}