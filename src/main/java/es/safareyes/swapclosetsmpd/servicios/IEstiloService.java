package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.EstiloCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Estilo;

public interface IEstiloService {
    Estilo crearEstilo(EstiloCrearDTO dto) throws Exception;
}