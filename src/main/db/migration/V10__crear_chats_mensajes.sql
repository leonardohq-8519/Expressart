CREATE TYPE tipo_archivo AS ENUM (
    'IMAGEN',
    'DOCUMENTO',
    'OTRO'
);

CREATE TABLE chats (
    id              BIGSERIAL   PRIMARY KEY,
    order_id        BIGINT      NOT NULL UNIQUE REFERENCES orders(id),
    redis_channel   VARCHAR(100) NOT NULL UNIQUE,
    fecha_creacion  TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE mensajes (
    id              BIGSERIAL       PRIMARY KEY,
    chat_id         BIGINT          NOT NULL REFERENCES chats(id),
    remitente_id    BIGINT          NOT NULL REFERENCES usuarios(id),
    contenido       TEXT,
    archivo_url     VARCHAR(500),
    tipo_archivo    tipo_archivo,
    leido           BOOLEAN         NOT NULL DEFAULT FALSE,
    persistido      BOOLEAN         NOT NULL DEFAULT FALSE,
    fecha_envio     TIMESTAMP       NOT NULL DEFAULT NOW(),
    fecha_lectura   TIMESTAMP,

    CONSTRAINT chk_mensaje_no_vacio
          CHECK (contenido IS NOT NULL OR archivo_url IS NOT NULL),

    CONSTRAINT chk_archivo_tiene_tipo
          CHECK (
              (archivo_url IS NULL AND tipo_archivo IS NULL) OR
              (archivo_url IS NOT NULL AND tipo_archivo IS NOT NULL)
          )
);

CREATE INDEX idx_chats_order_id ON chats(order_id);
CREATE INDEX idx_chats_redis_channel ON chats(redis_channel);
CREATE INDEX idx_mensajes_chat_id ON mensajes(chat_id);
CREATE INDEX idx_mensajes_remitente_id ON mensajes(remitente_id);
CREATE INDEX idx_mensajes_leido ON mensajes(leido);
CREATE INDEX idx_mensajes_persistido ON mensajes(persistido);
CREATE INDEX idx_mensajes_fecha_envio ON mensajes(fecha_envio);