package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionCrearDTO {
    private String mensaje;
    private String tipo;
    private Integer idPerfil;
}
