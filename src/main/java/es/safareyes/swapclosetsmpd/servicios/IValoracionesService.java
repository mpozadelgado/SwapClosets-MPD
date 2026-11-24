package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.ValoracionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Valoraciones;

public interface IValoracionesService {
    Valoraciones crearValoracion(ValoracionCrearDTO dto) throws Exception;
}
