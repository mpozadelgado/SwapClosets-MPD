package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PrendaCrearDTO {

    private String titulo;
    private String descripcion;
    private String talla;
    private String estado;
    private Boolean disponibilidad;
    private Integer idPerfil;
    private Integer idCategoria;
}
