package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PerfilCrearDTO {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private LocalDate fechaNacimiento;
    private String biografia;
    private String talla;
    private Integer usuarioId;
}
