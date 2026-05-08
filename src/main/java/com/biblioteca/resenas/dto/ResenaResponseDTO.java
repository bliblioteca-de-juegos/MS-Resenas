package com.biblioteca.resenas.dto;

import java.time.LocalDateTime;

public record ResenaResponseDTO(
        Long id,
        Long usuarioId,
        Long juegoId,
        String comentario,
        Integer calificacion,
        LocalDateTime creadaEn,
        LocalDateTime actualizadaEn,
        JuegoDTO juego
) {
}
