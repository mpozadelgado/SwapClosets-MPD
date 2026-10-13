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

import java.util.Optional;

@Repository
public interface IRepositorioPrenda extends JpaRepository<Prenda, Integer>, JpaSpecificationExecutor<Prenda> {


    Page<Prenda> findByDisponibilidadTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"perfil", "categoria"})
    @Query("SELECT p FROM Prenda p WHERE p.id = :id")
    Optional<Prenda> findByIdConDetalles(@Param("id") Integer id);

    long countByCategoriaIdAndDisponibilidadTrue(Integer categoriaId);
}
