package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tiemposolicitud")
    private String tiempoSolicitud;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechafin")
    private LocalDate fechaFin;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecharespuesta")
    private LocalDateTime fechaRespuesta;

    @ManyToOne
    @JoinColumn(name = "idperfil")
    private Perfil perfil;



}
