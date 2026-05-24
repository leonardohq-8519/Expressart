CREATE TABLE redes_sociales_artista (
    id BIGSERIAL PRIMARY KEY,
    perfil_artista_id BIGINT NOT NULL REFERENCES perfiles_artista(id),
    plataforma VARCHAR(50) NOT NULL,
    url VARCHAR(500) NOT NULL,

    CONSTRAINT uq_perfil_plataforma UNIQUE (perfil_artista_id, plataforma)
);