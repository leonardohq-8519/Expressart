CREATE TABLE perfiles_artista (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    tiempo_entrega_promedio INTEGER,
    rating_promedio DECIMAL(3,2),
    total_resenas INTEGER NOT NULL DEFAULT 0,
    ordenes_completadas INTEGER NOT NULL DEFAULT 0,
    stripe_id VARCHAR(255),
    stripe_onboarding BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE perfiles_cliente (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id),
    rating_promedio DECIMAL(3,2),
    total_resenas INTEGER NOT NULL DEFAULT 0,
    ordenes_realizadas INTEGER NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_perfiles_artista_usuario_id ON perfiles_artista(usuario_id);
CREATE INDEX idx_perfiles_artista_disponible ON perfiles_artista(disponible);
CREATE INDEX idx_perfiles_cliente_usuario_id ON perfiles_cliente(usuario_id);