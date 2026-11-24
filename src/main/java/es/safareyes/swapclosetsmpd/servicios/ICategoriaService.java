package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;

public interface ICategoriaService {
    Categoria crearCategoria(CategoriaCrearDTO dto) throws Exception;
}
