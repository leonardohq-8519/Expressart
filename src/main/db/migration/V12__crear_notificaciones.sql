CREATE TYPE tipo_notificacion AS ENUM (
    'ORDEN',
    'PAGO',
    'MENSAJE',
    'DEVOLUCION',
    'SOPORTE',
    'NUEVO_POST_FAVORITO',
    'SISTEMA'
);

CREATE TYPE tipo_correo AS ENUM (
    'VERIFICACION_EMAIL',
    'RECUPERACION_PASSWORD',
    'NUEVA_ORDEN',
    'ORDEN_ACEPTADA',
    'ORDEN_RECHAZADA',
    'PAGO_CONFIRMADO',
    'ORDEN_COMPLETADA',
    'DEVOLUCION',
    'SOPORTE'
);

CREATE TYPE estado_correo AS ENUM (
    'PENDIENTE',
    'ENVIADO',
    'FALLIDO'
);

CREATE TABLE notificaciones (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    tipo tipo_notificacion NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    url_destino VARCHAR(500),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_lectura TIMESTAMP
);

CREATE TABLE notificaciones_correo (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(id),
    destinatario_email VARCHAR(255) NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    tipo tipo_correo NOT NULL,
    estado estado_correo NOT NULL DEFAULT 'PENDIENTE',
    error TEXT,
    intentos INTEGER NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_envio TIMESTAMP,

    CONSTRAINT chk_max_intentos
        CHECK (intentos >= 0)
);

CREATE INDEX idx_notificaciones_usuario_id ON notificaciones(usuario_id);
CREATE INDEX idx_notificaciones_leida ON notificaciones(leida);
CREATE INDEX idx_notificaciones_tipo ON notificaciones(tipo);
CREATE INDEX idx_notificaciones_fecha_creacion ON notificaciones(fecha_creacion);

CREATE INDEX idx_notificaciones_correo_usuario_id ON notificaciones_correo(usuario_id);
CREATE INDEX idx_notificaciones_correo_estado ON notificaciones_correo(estado);
CREATE INDEX idx_notificaciones_correo_tipo ON notificaciones_correo(tipo);
CREATE INDEX idx_notificaciones_correo_fecha ON notificaciones_correo(fecha_creacion);