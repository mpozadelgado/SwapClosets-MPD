package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.CategoriaDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "prendasDisponibles", source = "prendasDisponibles")
    CategoriaDTO toDTO(Categoria categoria, Long prendasDisponibles);

    @Mapping(target = "id", ignore = true)
    Categoria toEntity(CategoriaCrearDTO dto);
}