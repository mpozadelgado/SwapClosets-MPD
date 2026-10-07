package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Mensajes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fechaenvio")
    private LocalDateTime fechaEnvio;

    @Column(name = "horaenvio")
    private LocalTime horaEnvio;

    @ManyToOne
    @JoinColumn(name = "idemisor")
    private Perfil emisor;

    @ManyToOne
    @JoinColumn(name = "idreceptor")
    private Perfil receptor;





}
