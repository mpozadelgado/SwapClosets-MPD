package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudCrearDTO {

    @NotBlank(message = "El tipo de solicitud es obligatorio")
    @Pattern(regexp = "INTERCAMBIO|PRESTAMO", message = "El tipo debe ser INTERCAMBIO o PRESTAMO")
    private String tiposolicitud;

    private LocalDate fechafin;

    @NotNull(message = "El perfil es obligatorio")
    private Integer idPerfil;

    @NotNull(message = "La prenda es obligatoria")
    private Integer idPrenda;

    private Integer idPrendaOfrecida;
}