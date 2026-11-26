package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.CategoriaDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;

import java.util.List;

public interface ICategoriaService {
    Categoria crearCategoria(CategoriaCrearDTO dto);
    List<CategoriaDTO> listar();
}