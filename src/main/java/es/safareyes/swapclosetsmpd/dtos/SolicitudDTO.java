package es.safareyes.swapclosetsmpd.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolicitudDTO(
        Integer id,
        String tipo,
        String estado,
        LocalDate fechaFin,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaRespuesta,
        Integer idPrenda,
        String tituloPrenda,
        Integer idPrendaOfrecida,
        String tituloPrendaOfrecida,
        Integer idSolicitante,
        String nombreSolicitante,
        Integer idPropietaria,
        String nombrePropietaria
) {
}