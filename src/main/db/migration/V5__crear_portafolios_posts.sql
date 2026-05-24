CREATE TABLE portafolios (
    id BIGSERIAL PRIMARY KEY,
    perfil_artista_id BIGINT NOT NULL REFERENCES perfiles_artista(id),
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    portada_url VARCHAR(500),
    es_publico BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    portafolio_id BIGINT NOT NULL REFERENCES portafolios(id),
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    es_publico BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_publicacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE imagenes_post (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL REFERENCES posts(id),
    url VARCHAR(500) NOT NULL,
    orden INTEGER NOT NULL DEFAULT 0
);