package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioFoto extends JpaRepository<Foto, Integer>{
}
