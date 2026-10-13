package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositorioNotificaciones extends JpaRepository<Notificaciones, Integer>{

    List<Notificaciones> findByPerfilIdOrderByFechaCreacionDesc(Integer perfilId);

    long countByPerfilIdAndLeidoFalse(Integer perfilId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notificaciones n SET n.leido = true WHERE n.perfil.id = :perfilId AND n.leido = false")
    int marcarTodasComoLeidasPorPerfil(@Param("perfilId") Integer perfilId);
}
