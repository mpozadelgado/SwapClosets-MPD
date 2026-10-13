package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioCategoria extends JpaRepository<Categoria, Integer>{
    boolean existsByNombreIgnoreCase(String nombre);
}
