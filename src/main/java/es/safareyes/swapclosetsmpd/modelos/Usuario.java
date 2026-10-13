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





public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "email")
    private String email;



}
