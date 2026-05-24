CREATE TABLE resenas_artista (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    cliente_id BIGINT NOT NULL REFERENCES usuarios(id),
    artista_id BIGINT NOT NULL REFERENCES usuarios(id),
    puntuacion SMALLINT NOT NULL,
    comentario TEXT,
    fecha_creacion TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_puntuacion_artista
    CHECK (puntuacion BETWEEN 1 AND 5),

    CONSTRAINT chk_cliente_distinto_artista_resena
    CHECK (cliente_id != artista_id)
);

CREATE TABLE resenas_cliente (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    artista_id BIGINT NOT NULL REFERENCES usuarios(id),
    cliente_id BIGINT NOT NULL REFERENCES usuarios(id),
    puntuacion SMALLINT NOT NULL,
    comentario TEXT,
    fecha_creacion  TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_puntuacion_cliente
    CHECK (puntuacion BETWEEN 1 AND 5),

    CONSTRAINT chk_artista_distinto_cliente_resena
    CHECK (artista_id != cliente_id)
);

CREATE INDEX idx_resenas_artista_order_id   ON resenas_artista(order_id);
CREATE INDEX idx_resenas_artista_artista_id ON resenas_artista(artista_id);
CREATE INDEX idx_resenas_artista_cliente_id ON resenas_artista(cliente_id);
CREATE INDEX idx_resenas_cliente_order_id   ON resenas_cliente(order_id);
CREATE INDEX idx_resenas_cliente_cliente_id ON resenas_cliente(cliente_id);
CREATE INDEX idx_resenas_cliente_artista_id ON resenas_cliente(artista_id);