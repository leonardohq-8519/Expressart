CREATE TYPE estado_ticket AS ENUM (
    'ABIERTO',
    'EN_REVISION',
    'RESUELTO',
    'CERRADO'
);

CREATE TYPE categoria_ticket AS ENUM (
    'ORDEN',
    'PAGO',
    'CUENTA',
    'OTRO'
);

CREATE TABLE tickets_soporte (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    order_id BIGINT REFERENCES orders(id),
    estado estado_ticket NOT NULL DEFAULT 'ABIERTO',
    categoria categoria_ticket NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    respuesta TEXT,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_resolucion TIMESTAMP,

    CONSTRAINT chk_ticket_orden_tiene_order_id
     CHECK (
         (categoria = 'ORDEN' AND order_id IS NOT NULL) OR
         (categoria != 'ORDEN')
         )
);

CREATE INDEX idx_tickets_soporte_usuario_id ON tickets_soporte(usuario_id);
CREATE INDEX idx_tickets_soporte_order_id ON tickets_soporte(order_id);
CREATE INDEX idx_tickets_soporte_estado ON tickets_soporte(estado);
CREATE INDEX idx_tickets_soporte_categoria ON tickets_soporte(categoria);
CREATE INDEX idx_tickets_soporte_fecha_creacion ON tickets_soporte(fecha_creacion);