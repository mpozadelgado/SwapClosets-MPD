package es.safareyes.swapclosetsmpd.dtos;

public record ActividadCategoriaDTO(
        String categoria,
        String mes,
        Long publicaciones,
        Long intercambios,
        Long prestamos
) {
}