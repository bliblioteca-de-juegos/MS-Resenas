package com.biblioteca.resenas.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.biblioteca.resenas.client.JuegoClient;
import com.biblioteca.resenas.client.UsuarioClient;
import com.biblioteca.resenas.dto.JuegoDTO;
import com.biblioteca.resenas.dto.ResenaRequestDTO;
import com.biblioteca.resenas.dto.ResenaResponseDTO;
import com.biblioteca.resenas.model.Resena;
import com.biblioteca.resenas.repository.ResenaRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    @Autowired
    private JuegoClient juegoClient;
    @Autowired
    private UsuarioClient usuarioClient;
    public List<ResenaResponseDTO> obtenerTodas() {
        return resenaRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
    public Optional<ResenaResponseDTO> obtenerPorId(Long id) {
        return resenaRepository.findById(id).map(this::mapToDTO);
    }
    public List<ResenaResponseDTO> obtenerPorJuego(Long juegoId) {
        validarJuego(juegoId);
        return resenaRepository.findByJuegoId(juegoId).stream()
                .map(this::mapToDTO)
                .toList();
    }
    public List<ResenaResponseDTO> obtenerPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return resenaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToDTO)
                .toList();
    }
    public Optional<ResenaResponseDTO> obtenerPorUsuarioYJuego(Long usuarioId, Long juegoId) {
        validarUsuario(usuarioId);
        validarJuego(juegoId);
        return resenaRepository.findByUsuarioIdAndJuegoId(usuarioId, juegoId)
                .map(this::mapToDTO);
    }
    @Transactional
    public ResenaResponseDTO crear(ResenaRequestDTO dto) {
        validarUsuario(dto.getUsuarioId());
        validarJuego(dto.getJuegoId());
        if (resenaRepository.existsByUsuarioIdAndJuegoId(dto.getUsuarioId(), dto.getJuegoId())) {
            throw new IllegalArgumentException("El usuario ya tiene una resena para este juego");
        }
        Resena resena = new Resena(
                null,
                dto.getUsuarioId(),
                dto.getJuegoId(),
                dto.getComentario(),
                dto.getCalificacion(),
                LocalDateTime.now(),
                null
        );
        return mapToDTO(resenaRepository.save(resena));
    }
    @Transactional
    public Optional<ResenaResponseDTO> actualizar(Long id, ResenaRequestDTO dto) {
        validarUsuario(dto.getUsuarioId());
        validarJuego(dto.getJuegoId());
        return resenaRepository.findById(id).map(resena -> {
            resena.setUsuarioId(dto.getUsuarioId());
            resena.setJuegoId(dto.getJuegoId());
            resena.setComentario(dto.getComentario());
            resena.setCalificacion(dto.getCalificacion());
            resena.setActualizadaEn(LocalDateTime.now());
            return mapToDTO(resenaRepository.save(resena));
        });
    }
    @Transactional
    public void eliminar(Long id) {
        if (!resenaRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una resena con id " + id);
        }
        resenaRepository.deleteById(id);
    }
    private ResenaResponseDTO mapToDTO(Resena resena) {
        return new ResenaResponseDTO(
                resena.getId(),
                resena.getUsuarioId(),
                resena.getJuegoId(),
                resena.getComentario(),
                resena.getCalificacion(),
                resena.getCreadaEn(),
                resena.getActualizadaEn(),
                obtenerJuegoSeguro(resena.getJuegoId())
        );
    }
    private void validarUsuario(Long usuarioId) {
        try {
            usuarioClient.obtenerUsuario(usuarioId);
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("No existe un usuario con id " + usuarioId);
        }
    }
    private void validarJuego(Long juegoId) {
        try {
            juegoClient.obtenerJuego(juegoId);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("No existe un juego con id " + juegoId);
        }
    }
    private JuegoDTO obtenerJuegoSeguro(Long juegoId) {
        try {
            return juegoClient.obtenerJuego(juegoId);
        } catch (FeignException e) {
            return null;
        }
    }
}
