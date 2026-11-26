package es.safareyes.swapclosetsmpd.dtos;

import java.time.LocalDateTime;

public record ValoracionDTO(
        Integer id,
        Integer puntuacion,
        String comentario,
        LocalDateTime fechaCreacion,
        Integer idAutor,
        String nombreAutor,
        Integer idDestinatario,
        String nombreDestinatario
) {
}