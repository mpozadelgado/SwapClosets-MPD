package es.safareyes.swapclosetsmpd.dtos;

import java.util.List;

public record PerfilPublicoDTO(
        Integer id,
        String nombre,
        String apellido1,
        String biografia,
        String talla,
        List<String> estilos,
        Double reputacion,
        Long valoraciones,
        List<PrendaTarjetaDTO> prendas
) {
}