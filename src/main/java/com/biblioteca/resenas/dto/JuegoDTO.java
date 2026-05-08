package com.biblioteca.resenas.dto;

public record JuegoDTO(
        Long id,
        String nombre,
        String titulo,
        String descripcion,
        Double precio
) {
}
