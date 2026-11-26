package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Prenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.safareyes.swapclosetsmpd.dtos.ActividadCategoriaView;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface IRepositorioPrenda extends JpaRepository<Prenda, Integer>, JpaSpecificationExecutor<Prenda> {


    Page<Prenda> findByDisponibilidadTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"perfil", "categoria"})
    @Query("SELECT p FROM Prenda p WHERE p.id = :id")
    Optional<Prenda> findByIdConDetalles(@Param("id") Integer id);

    long countByCategoriaIdAndDisponibilidadTrue(Integer categoriaId);

    List<Prenda> findByPerfilIdAndDisponibilidadTrue(Integer perfilId);

    @Query(value = """
            SELECT categoria, mes,
                   SUM(publicaciones) AS publicaciones,
                   SUM(intercambios) AS intercambios,
                   SUM(prestamos) AS prestamos
            FROM (
                SELECT c.nombre AS categoria,
                       TO_CHAR(DATE_TRUNC('month', p.fechapublicacion), 'YYYY-MM') AS mes,
                       1 AS publicaciones, 0 AS intercambios, 0 AS prestamos
                FROM prenda p
                JOIN categoria c ON c.id = p.id_categoria
                WHERE CAST(p.fechapublicacion AS date) BETWEEN :desde AND :hasta
                UNION ALL
                SELECT c.nombre,
                       TO_CHAR(DATE_TRUNC('month', s.fechacreacion), 'YYYY-MM'),
                       0,
                       CASE WHEN s.tiposolicitud = 'INTERCAMBIO' THEN 1 ELSE 0 END,
                       CASE WHEN s.tiposolicitud = 'PRESTAMO' THEN 1 ELSE 0 END
                FROM solicitudes s
                JOIN prenda p ON p.id = s.id_prenda
                JOIN categoria c ON c.id = p.id_categoria
                WHERE s.estado IN ('ACEPTADA', 'FINALIZADA', 'RETRASADA')
                  AND CAST(s.fechacreacion AS date) BETWEEN :desde AND :hasta
            ) actividad
            GROUP BY categoria, mes
            ORDER BY mes, categoria
            """, nativeQuery = true)
    List<ActividadCategoriaView> actividadPorCategoriaYMes(@Param("desde")
                                                           LocalDate desde,
                                                           @Param("hasta") LocalDate hasta);
}
