package com.biblioteca.resenas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.biblioteca.resenas.dto.ResenaRequestDTO;
import com.biblioteca.resenas.dto.ResenaResponseDTO;
import com.biblioteca.resenas.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v2/resenas")
@Tag(name = "Resenas", description = "Operaciones de resenas y calificaciones")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;
    @GetMapping
    @Operation(summary = "Listar todas las resenas")
    public List<ResenaResponseDTO> obtenerTodas() {
        return resenaService.obtenerTodas();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una resena por ID")
    public ResponseEntity<ResenaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/juego/{juegoId}")
    @Operation(summary = "Listar resenas por juego")
    public List<ResenaResponseDTO> obtenerPorJuego(@PathVariable Long juegoId) {
        return resenaService.obtenerPorJuego(juegoId);
    }
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar resenas por usuario")
    public List<ResenaResponseDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return resenaService.obtenerPorUsuario(usuarioId);
    }
    @GetMapping("/usuario/{usuarioId}/juego/{juegoId}")
    @Operation(summary = "Obtener la resena de un usuario para un juego")
    public ResponseEntity<ResenaResponseDTO> obtenerPorUsuarioYJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId) {
        return resenaService.obtenerPorUsuarioYJuego(usuarioId, juegoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    @Operation(summary = "Crear una resena")
    public ResponseEntity<ResenaResponseDTO> crear(@Valid @RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(dto));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una resena")
    public ResponseEntity<ResenaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResenaRequestDTO dto) {
        return resenaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una resena")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
