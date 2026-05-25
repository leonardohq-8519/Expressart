CREATE TYPE estado_order AS ENUM (
    'PENDIENTE',
    'ACEPTADA',
    'RECHAZADA',
    'EN_PROGRESO',
    'EN_REVISION',
    'COMPLETADA',
    'DEVOLUCION'
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES usuarios(id),
    artista_id BIGINT NOT NULL REFERENCES usuarios(id),
    opcion_comision_id BIGINT NOT NULL REFERENCES opciones_comision(id),
    estado estado_order NOT NULL DEFAULT 'PENDIENTE',
    precio_final DECIMAL(10,2) NOT NULL,
    descripcion_trabajo TEXT NOT NULL,
    archivo_entrega_url VARCHAR(500), fecha_creacion      TIMESTAMP       NOT NULL DEFAULT NOW(),
    fecha_limite TIMESTAMP NOT NULL,
    fecha_completada TIMESTAMP,

    CONSTRAINT chk_cliente_distinto_artista
        CHECK (cliente_id != artista_id)
);

CREATE INDEX idx_orders_cliente_id ON orders(cliente_id);
CREATE INDEX idx_orders_artista_id ON orders(artista_id);
CREATE INDEX idx_orders_opcion_comision_id ON orders(opcion_comision_id);
CREATE INDEX idx_orders_estado ON orders(estado);
CREATE INDEX idx_orders_fecha_limite ON orders(fecha_limite);