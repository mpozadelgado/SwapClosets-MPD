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


public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;


}
