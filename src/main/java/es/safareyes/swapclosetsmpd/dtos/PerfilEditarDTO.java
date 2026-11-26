package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PerfilEditarDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String nombre,

        @Size(max = 50, message = "El primer apellido no puede tener más de 50 caracteres")
        String apellido1,

        @Size(max = 50, message = "El segundo apellido no puede tener más de 50 caracteres")
        String apellido2,

        @Size(max = 500, message = "La biografía no puede tener más de 500 caracteres")
        String biografia,

        @Size(max = 10, message = "La talla no puede tener más de 10 caracteres")
        String talla,

        @NotNull(message = "Los estilos son obligatorios")
        @Size(min = 1, max = 5, message = "Un perfil tiene entre 1 y 5 estilos")
        List<Integer> idsEstilos
) {
}