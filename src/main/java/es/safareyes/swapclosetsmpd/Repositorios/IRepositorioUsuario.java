package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioUsuario extends JpaRepository<Usuario, Integer>{
}
