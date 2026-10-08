package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioValoraciones extends JpaRepository<Valoraciones, Integer>{
}
