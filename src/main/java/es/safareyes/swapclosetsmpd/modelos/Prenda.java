package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Prenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Prenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "talla")
    private String talla;

    @Column(name = "estado")
    private String estado;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Column(name = "prendaintercambio")
    private String prendaIntercambio;

    @Column(name = "fechapublicacion")
    private LocalDate fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "idperfil")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "idcategoria")
    private Categoria categoria;


}
