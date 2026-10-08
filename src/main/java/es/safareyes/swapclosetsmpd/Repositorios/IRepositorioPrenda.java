package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioPrenda extends JpaRepository<Prenda, Integer>{
}
