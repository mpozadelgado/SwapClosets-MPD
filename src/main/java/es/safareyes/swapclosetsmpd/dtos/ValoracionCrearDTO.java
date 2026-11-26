package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValoracionCrearDTO {

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación debe estar entre 1 y 5")
    @Max(value = 5, message = "La puntuación debe estar entre 1 y 5")
    private Integer puntuacion;

    @Size(max = 500, message = "El comentario no puede tener más de 500 caracteres")
    private String comentario;

    private Integer idSolicitud;

    @NotNull(message = "El autor es obligatorio")
    private Integer idAutor;

    private Integer idDestinatario;
}