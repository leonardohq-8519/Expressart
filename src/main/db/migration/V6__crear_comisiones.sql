CREATE TABLE comisiones (
    id BIGSERIAL PRIMARY KEY,
    perfil_artista_id BIGINT NOT NULL REFERENCES perfiles_artista(id),
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    portada_url VARCHAR(500),
    esta_activa BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE opciones_comision (
    id BIGSERIAL PRIMARY KEY,
    comision_id BIGINT NOT NULL REFERENCES comisiones(id),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    tiempo_entrega INTEGER NOT NULL,
    numero_revisiones INTEGER NOT NULL DEFAULT 1,
    incluye_archivo_fuente BOOLEAN NOT NULL DEFAULT FALSE,
    esta_activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE imagenes_comision (
    id BIGSERIAL PRIMARY KEY,
    comision_id BIGINT NOT NULL REFERENCES comisiones(id),
    url VARCHAR(500) NOT NULL,
    orden INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE comision_categoria (
    comision_id BIGINT NOT NULL REFERENCES comisiones(id),
    categoria_id BIGINT NOT NULL REFERENCES categorias(id),

    PRIMARY KEY (comision_id, categoria_id)
);

CREATE TABLE comision_tag (
    comision_id BIGINT NOT NULL REFERENCES comisiones(id),
    tag_id BIGINT NOT NULL REFERENCES tags(id),

    PRIMARY KEY (comision_id, tag_id)
);

CREATE INDEX idx_comisiones_perfil_artista_id ON comisiones(perfil_artista_id);
CREATE INDEX idx_comisiones_esta_activa ON comisiones(esta_activa);
CREATE INDEX idx_opciones_comision_comision_id ON opciones_comision(comision_id);
CREATE INDEX idx_opciones_comision_esta_activa ON opciones_comision(esta_activa);
CREATE INDEX idx_imagenes_comision_comision_id ON imagenes_comision(comision_id);
CREATE INDEX idx_comision_categoria_categoria_id ON comision_categoria(categoria_id);
CREATE INDEX idx_comision_tag_tag_id ON comision_tag(tag_id);

ALTER TABLE opciones_comision
    ADD CONSTRAINT chk_precio_positivo CHECK (precio > 0);

ALTER TABLE opciones_comision
    ADD CONSTRAINT chk_tiempo_entrega_positivo CHECK (tiempo_entrega > 0);