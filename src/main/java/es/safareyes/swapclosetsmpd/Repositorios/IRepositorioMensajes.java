package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositorioMensajes extends JpaRepository<Mensajes, Integer>{

    List<Mensajes> findBySolicitudIdOrderByFechaEnvioAsc(Integer solicitudId);

    long countBySolicitudIdAndEmisorIdNotAndLeidoFalse(Integer solicitudId, Integer emisorId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Mensajes m SET m.leido = true WHERE m.solicitud.id = :solicitudId AND m.emisor.id <> :perfilId AND m.leido = false")
    int marcarMensajesComoLeidos(@Param("solicitudId") Integer solicitudId, @Param("perfilId") Integer perfilId);
}
