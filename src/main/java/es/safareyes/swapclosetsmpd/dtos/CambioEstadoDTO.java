package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CambioEstadoDTO(

        @NotBlank(message = "El estado es obligatorio")
        String estado,

        @NotNull(message = "El perfil es obligatorio")
        Integer idPerfil
) {
}