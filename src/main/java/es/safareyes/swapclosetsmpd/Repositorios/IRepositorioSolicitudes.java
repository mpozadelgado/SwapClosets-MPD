package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioSolicitudes extends JpaRepository<Solicitudes, Integer>{
}
