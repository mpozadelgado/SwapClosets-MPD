package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.SolicitudDTO;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    @Mapping(target = "tipo", source = "tiposolicitud")
    @Mapping(target = "idPrenda", source = "prenda.id")
    @Mapping(target = "tituloPrenda", source = "prenda.titulo")
    @Mapping(target = "idPrendaOfrecida", source = "prendaOfrecida.id")
    @Mapping(target = "tituloPrendaOfrecida", source = "prendaOfrecida.titulo")
    @Mapping(target = "idSolicitante", source = "perfil.id")
    @Mapping(target = "nombreSolicitante", source = "perfil.nombre")
    @Mapping(target = "idPropietaria", source = "prenda.perfil.id")
    @Mapping(target = "nombrePropietaria", source = "prenda.perfil.nombre")
    SolicitudDTO toDTO(Solicitudes solicitud);
}