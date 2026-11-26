package es.safareyes.swapclosetsmpd.dtos;

import java.time.LocalDate;
import java.util.List;

public record PrendaDetalleDTO(
        Integer id,
        String titulo,
        String descripcion,
        String talla,
        String estado,
        Boolean disponibilidad,
        String prendaIntercambio,
        LocalDate fechaPublicacion,
        String categoria,
        List<String> fotos,
        PropietariaDTO propietaria
) {
}