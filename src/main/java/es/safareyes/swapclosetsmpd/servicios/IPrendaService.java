package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaDetalleDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaTarjetaDTO;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import org.springframework.data.domain.Page;

public interface IPrendaService {
    Prenda publicarPrenda(PrendaCrearDTO dto);
    PrendaDetalleDTO crear(PrendaCrearDTO dto);
    PrendaDetalleDTO obtener(Integer id);
    Page<PrendaTarjetaDTO> buscar(String texto, Integer idCategoria, String talla, int page, int size);
    PrendaDetalleDTO editar(Integer id, PrendaCrearDTO dto);
    void retirar(Integer id);
}