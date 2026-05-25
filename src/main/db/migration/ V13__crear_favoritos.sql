CREATE TABLE usuario_favoritos (
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    artista_id BIGINT NOT NULL REFERENCES usuarios(id),

    PRIMARY KEY (usuario_id, artista_id),

    CONSTRAINT chk_usuario_distinto_artista_favorito
    CHECK (usuario_id != artista_id)
);


CREATE INDEX idx_usuario_favoritos_usuario_id ON usuario_favoritos(usuario_id);
CREATE INDEX idx_usuario_favoritos_artista_id ON usuario_favoritos(artista_id);