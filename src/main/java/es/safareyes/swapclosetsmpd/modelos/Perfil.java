package es.safareyes.swapclosetsmpd.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Perfil")
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
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "perfil_estilo",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_estilo")
    )
    private List<Estilo> estilos;
}
