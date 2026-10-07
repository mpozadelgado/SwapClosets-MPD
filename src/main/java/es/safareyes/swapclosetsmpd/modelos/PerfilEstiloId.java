package es.safareyes.swapclosetsmpd.modelos;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode


public class PerfilEstiloId implements Serializable{
    @Column(name = "idperfil")
    private Integer idPerfil;

    @Column(name = "idestilo")
    private Integer idEstilo;

}
