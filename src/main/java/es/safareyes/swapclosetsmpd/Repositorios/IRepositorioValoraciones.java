package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositorioValoraciones extends JpaRepository<Valoraciones, Integer>{

    List<Valoraciones> findByDestinatarioId(Integer destinatarioId);

    List<Valoraciones> findByAutorId(Integer autorId);

    boolean existsBySolicitudIdAndAutorId(Integer solicitudId, Integer autorId);

    @Query("SELECT AVG(v.puntuacion) FROM Valoraciones v WHERE v.destinatario.id = :destinatarioId")
    Double obtenerPuntuacionMediaPerfil(@Param("destinatarioId") Integer destinatarioId);

    long countByDestinatarioId(Integer destinatarioId);
}
