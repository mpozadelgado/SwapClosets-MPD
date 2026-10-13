package es.safareyes.swapclosetsmpd.Repositorios;


import es.safareyes.swapclosetsmpd.modelos.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepositorioPerfil extends JpaRepository<Perfil, Integer>{

    boolean existsByEmailIgnoreCase(String email);

    Optional<Perfil> findByUsuarioId(Integer idUsuario);


    @Query("SELECT p FROM Perfil p " +
            "JOIN p.estilos e " +
            "WHERE e.id IN :estiloIds AND p.id <> :miPerfilId " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(e.id) DESC")
    List<Perfil> findPerfilesAfines(@Param("estiloIds") List<Integer> estiloIds,
                                    @Param("miPerfilId") Integer miPerfilId);
}
