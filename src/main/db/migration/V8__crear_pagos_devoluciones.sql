CREATE TYPE estado_pago AS ENUM (
    'PENDIENTE',
    'PROCESANDO',
    'COMPLETADO',
    'FALLIDO',
    'REEMBOLSADO'
);

CREATE TYPE estado_devolucion AS ENUM (
    'SOLICITADA',
    'EN_REVISION',
    'APROBADA',
    'RECHAZADA',
    'REEMBOLSADA'
);

CREATE TABLE pagos (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    estado estado_pago NOT NULL DEFAULT 'PENDIENTE',
    monto DECIMAL(10,2) NOT NULL,
    monto_artista DECIMAL(10,2) NOT NULL,
    monto_comision_plataforma DECIMAL(10,2) NOT NULL,
    moneda VARCHAR(3) NOT NULL DEFAULT 'USD',
    stripe_payment_intent_id VARCHAR(255) NOT NULL UNIQUE,
    stripe_transfer_id VARCHAR(255) UNIQUE,
    fecha_pago TIMESTAMP,
    fecha_transferencia TIMESTAMP,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),

    -- El monto total debe ser igual a la suma de sus partes
    CONSTRAINT chk_monto_positivo
    CHECK (monto > 0),
    CONSTRAINT chk_montos_coherentes
    CHECK (monto = monto_artista + monto_comision_plataforma)
);

CREATE TABLE devoluciones (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    estado estado_devolucion NOT NULL DEFAULT 'SOLICITADA',
    motivo TEXT NOT NULL,
    respuesta_artista TEXT,
    monto_reembolso DECIMAL(10,2) NOT NULL,
    stripe_refund_id VARCHAR(255) UNIQUE,
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_resolucion TIMESTAMP,

    CONSTRAINT chk_monto_reembolso_positivo
      CHECK (monto_reembolso > 0)
);

CREATE INDEX idx_pagos_order_id ON pagos(order_id);
CREATE INDEX idx_pagos_estado ON pagos(estado);
CREATE INDEX idx_devoluciones_order_id ON devoluciones(order_id);
CREATE INDEX idx_devoluciones_estado ON devoluciones(estado);