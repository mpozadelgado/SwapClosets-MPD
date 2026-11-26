package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.MensajeDTO;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MensajeMapper {

    @Mapping(target = "idEmisor", source = "emisor.id")
    @Mapping(target = "nombreEmisor", source = "emisor.nombre")
    MensajeDTO toDTO(Mensajes mensaje);
}