package es.safareyes.swapclosetsmpd.dtos;

public record CategoriaDTO(
        Integer id,
        String nombre,
        Long prendasDisponibles
) {
}