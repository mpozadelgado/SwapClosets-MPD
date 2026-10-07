package es.safareyes.swapclosetsmpd.modelos;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "orden")
    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "idprenda")
    private Prenda prenda;

}
