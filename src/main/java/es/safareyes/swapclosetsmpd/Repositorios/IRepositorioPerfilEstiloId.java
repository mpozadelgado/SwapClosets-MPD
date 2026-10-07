package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.PerfilEstiloId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositorioPerfilEstiloId extends JpaRepository<PerfilEstiloId, Integer>{
}
