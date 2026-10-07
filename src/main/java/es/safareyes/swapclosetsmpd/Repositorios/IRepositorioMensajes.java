package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Foto;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioMensajes extends JpaRepository<Mensajes, Integer>{
}
