CREATE TABLE resenas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    juego_id BIGINT NOT NULL,
    comentario VARCHAR(1000) NOT NULL,
    calificacion INT NOT NULL,
    creada_en DATETIME NOT NULL,
    actualizada_en DATETIME,
    CONSTRAINT uk_resena_usuario_juego UNIQUE (usuario_id, juego_id)
);
