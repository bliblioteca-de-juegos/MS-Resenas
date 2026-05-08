package com.biblioteca.resenas.repository;

import com.biblioteca.resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByJuegoId(Long juegoId);

    List<Resena> findByUsuarioId(Long usuarioId);

    Optional<Resena> findByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);

    boolean existsByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);
}
