package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Valoraciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "idsolicitud")
    private Solicitudes solicitud;

    @ManyToMany
    @JoinTable(
            name = "perfil_estilo",
            joinColumns = @JoinColumn(name = "idperfil"),
            inverseJoinColumns = @JoinColumn(name = "idestilo")
    )
    private List<Estilo> estilos;

}
