package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "leido")
    private Boolean leido;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "idperfil")
    private Perfil perfil;
}
