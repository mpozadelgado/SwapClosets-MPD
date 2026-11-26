package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaCrearDTO {

    @NotBlank(message = "El título de la prenda es obligatorio")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String titulo;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String descripcion;

    @NotBlank(message = "La talla es obligatoria")
    private String talla;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private Boolean disponibilidad;

    @NotNull(message = "El perfil es obligatorio")
    private Integer idPerfil;

    @NotNull(message = "La categoría es obligatoria")
    private Integer idCategoria;
}