package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.PerfilEditarDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PerfilMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "fechaNacimiento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "estilos", ignore = true)
    void editarDatos(@MappingTarget Perfil perfil, PerfilEditarDTO dto);
}