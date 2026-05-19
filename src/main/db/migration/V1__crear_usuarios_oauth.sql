CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(255)    NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    nombre_completo VARCHAR(100) NOT NULL,
    avatar_url VARCHAR(500),
    biografia TEXT,
    fecha_registro TIMESTAMP NOT NULL DEFAULT NOW(),
    esta_activo BOOLEAN NOT NULL DEFAULT TRUE,
    email_verificado BOOLEAN NOT NULL DEFAULT FALSE,
    token_version INTEGER NOT NULL DEFAULT 1
);

CREATE TABLE cuentas_oauth (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    proveedor VARCHAR(50) NOT NULL,
    proveedor_id VARCHAR(255) NOT NULL,
    email_oauth VARCHAR(255),
    fecha_vinculacion TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_proveedor_proveedor_id UNIQUE (proveedor, proveedor_id)
);