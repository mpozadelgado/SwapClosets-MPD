package es.safareyes.swapclosetsmpd.Repositorios;

import es.safareyes.swapclosetsmpd.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioUsuario extends JpaRepository<Usuario, Integer>{

    boolean existsByUsernameIgnoreCase(String username);

    Optional<Usuario> findByUsername(String username);

    boolean existsByEmail(String email);
}
