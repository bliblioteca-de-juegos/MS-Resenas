package com.biblioteca.resenas.controller;

import com.biblioteca.resenas.dto.ResenaRequestDTO;
import com.biblioteca.resenas.dto.ResenaResponseDTO;
import com.biblioteca.resenas.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @GetMapping
    public List<ResenaResponseDTO> obtenerTodas() {
        return resenaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/juego/{juegoId}")
    public List<ResenaResponseDTO> obtenerPorJuego(@PathVariable Long juegoId) {
        return resenaService.obtenerPorJuego(juegoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ResenaResponseDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return resenaService.obtenerPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/juego/{juegoId}")
    public ResponseEntity<ResenaResponseDTO> obtenerPorUsuarioYJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId) {
        return resenaService.obtenerPorUsuarioYJuego(usuarioId, juegoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crear(@Valid @RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResenaRequestDTO dto) {
        return resenaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
