package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ValoracionCrearDTO {
    private Integer puntuacion;
    private String comentario;
    private Integer idSolicitud;
    private Integer idAutor;
    private Integer idDestinatario;
}
