package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "apellido1")
    private String apellido1;

    @Column (name = "apellido2")
    private String apellido2;

    @Column (name = "email")
    private String email;

    @Column (name = "fechanacimiento")
    private LocalDate fechaNacimiento;

    @Column (name = "biografia")
    private String biografia;

    @Column (name = "talla")
    private String talla;

    @OneToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "perfil_estilo",
            joinColumns = @JoinColumn(name = "idperfil"),
            inverseJoinColumns = @JoinColumn(name = "idestilo")
    )
    private List<Estilo> estilos;
}
