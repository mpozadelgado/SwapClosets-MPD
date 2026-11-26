package es.safareyes.swapclosetsmpd.conversores;

import es.safareyes.swapclosetsmpd.dtos.PrendaDetalleDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaTarjetaDTO;
import es.safareyes.swapclosetsmpd.dtos.PropietariaDTO;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrendaMapper {

    @Mapping(target = "categoria", source = "prenda.categoria.nombre")
    @Mapping(target = "foto", source = "foto")
    PrendaTarjetaDTO toTarjeta(Prenda prenda, String foto);

    @Mapping(target = "id", source = "prenda.id")
    @Mapping(target = "categoria", source = "prenda.categoria.nombre")
    @Mapping(target = "fotos", source = "fotos")
    @Mapping(target = "propietaria", source = "propietaria")
    PrendaDetalleDTO toDetalle(Prenda prenda, List<String> fotos, PropietariaDTO propietaria);
}