package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SolicitudCrearDTO {
    private String tiposolicitud;
    private String tiemposolicitud;
    private LocalDate fechafin;
    private Integer idPerfil;
    private Integer idPrenda;
}
