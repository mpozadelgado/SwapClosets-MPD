package es.safareyes.swapclosetsmpd.Repositorios;

import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IRepositorioSolicitudes extends JpaRepository<Solicitudes, Integer>{


    @Query("SELECT COUNT(s) > 0 FROM Solicitudes s WHERE s.perfil.id = :perfilId AND s.prenda.id = :prendaId AND s.estado = :estado")
    boolean existeSolicitudActiva(@Param("perfilId") Integer perfilId,
                                  @Param("prendaId") Integer prendaId,
                                  @Param("estado") String estado);

    @Query("SELECT COUNT(s) FROM Solicitudes s WHERE s.perfil.id = :perfilId AND s.tiposolicitud = :tipoSolicitud AND s.estado IN :estados")
    long contarPrestamosActivos(@Param("perfilId") Integer perfilId,
                                @Param("tipoSolicitud") String tipoSolicitud,
                                @Param("estados") List<String> estados);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Solicitudes s SET s.estado = 'RECHAZADA' " +
            "WHERE s.prenda.id = :prendaId AND s.id <> :solicitudAceptadaId AND s.estado = 'PENDIENTE'")
    int rechazarDemasSolicitudesPendientes(@Param("prendaId") Integer prendaId,
                                           @Param("solicitudAceptadaId") Integer solicitudAceptadaId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Solicitudes s SET s.estado = 'CADUCADA' " +
            "WHERE s.estado = 'PENDIENTE' AND s.fechaCreacion <= :fechaLimite")
    int caducarSolicitudesAntiguas(@Param("fechaLimite") LocalDateTime fechaLimite);

}
