package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MensajeCrearDTO {
    private String contenido;
    private Integer idEmisor;
    private Integer idReceptor;
}
