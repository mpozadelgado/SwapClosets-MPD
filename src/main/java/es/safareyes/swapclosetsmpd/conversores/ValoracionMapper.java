package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.ValoracionDTO;
import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ValoracionMapper {

    @Mapping(target = "idAutor", source = "autor.id")
    @Mapping(target = "nombreAutor", source = "autor.nombre")
    @Mapping(target = "idDestinatario", source = "destinatario.id")
    @Mapping(target = "nombreDestinatario", source = "destinatario.nombre")
    ValoracionDTO toDTO(Valoraciones valoracion);
}