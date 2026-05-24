CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    icono_url VARCHAR(500)
    );

CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE INDEX idx_categorias_nombre ON categorias(nombre);
CREATE INDEX idx_tags_nombre ON tags(nombre);