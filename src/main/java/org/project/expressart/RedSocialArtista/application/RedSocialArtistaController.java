package org.project.expressart.RedSocialArtista.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.RedSocialArtista.domain.ArtistSocialMediaService;
import org.project.expressart.RedSocialArtista.domain.RedSocialArtista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/red-social-artista")
@RequiredArgsConstructor
public class RedSocialArtistaController {

    private final ArtistSocialMediaService artistSocialMediaService;

    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<RedSocialArtista>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(artistSocialMediaService.findByArtistaId(artistaId));
    }

    @PostMapping
    public ResponseEntity<RedSocialArtista> create(@RequestBody RedSocialArtista redSocial) {
        return ResponseEntity.ok(artistSocialMediaService.create(redSocial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistSocialMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}