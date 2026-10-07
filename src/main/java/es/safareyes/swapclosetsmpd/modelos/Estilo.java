package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Estilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "estilos")
    private List<Perfil> perfiles;
}
