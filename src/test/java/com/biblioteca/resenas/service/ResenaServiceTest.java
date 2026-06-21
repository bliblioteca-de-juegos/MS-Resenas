package com.biblioteca.resenas.service;

import com.biblioteca.resenas.client.JuegoClient;
import com.biblioteca.resenas.client.UsuarioClient;
import com.biblioteca.resenas.dto.ResenaResponseDTO;
import com.biblioteca.resenas.model.Resena;
import com.biblioteca.resenas.repository.ResenaRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;
    @Mock
    private JuegoClient juegoClient;
    @Mock
    private UsuarioClient usuarioClient;
    @InjectMocks
    private ResenaService resenaService;

    private final Faker faker = new Faker();

    @Test
    void obtenerPorIdRetornaLaResena() {
        Long id = faker.number().numberBetween(1L, 1000L);
        String comentario = faker.lorem().sentence();
        Resena resena = new Resena(id, 10L, 20L, comentario, 5, LocalDateTime.now(), null);
        when(resenaRepository.findById(id)).thenReturn(Optional.of(resena));

        Optional<ResenaResponseDTO> resultado = resenaService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(comentario, resultado.get().comentario());
        assertEquals(5, resultado.get().calificacion());
    }

    @Test
    void eliminarLanzaExcepcionCuandoLaResenaNoExiste() {
        Long id = faker.number().numberBetween(1L, 1000L);
        when(resenaRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> resenaService.eliminar(id));
        verify(resenaRepository, never()).deleteById(id);
    }
}
