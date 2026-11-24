package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Prenda;

public interface IPrendaService {
    Prenda publicarPrenda(PrendaCrearDTO dto) throws Exception;
}
