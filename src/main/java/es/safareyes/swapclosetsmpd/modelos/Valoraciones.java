package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Valoraciones")
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

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Perfil autor;

    @ManyToOne
    @JoinColumn(name = "id_destinatario")
    private Perfil destinatario;

}
