package es.safareyes.swapclosetsmpd.dtos;

import java.time.LocalDateTime;

public record MensajeDTO(
        Integer id,
        String contenido,
        LocalDateTime fechaEnvio,
        Integer idEmisor,
        String nombreEmisor,
        Boolean leido
) {
}