package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.PerfilCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.PerfilEditarDTO;
import es.safareyes.swapclosetsmpd.dtos.PerfilPublicoDTO;
import es.safareyes.swapclosetsmpd.dtos.PropietariaDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;

import java.util.List;

public interface IPerfilService {
    Perfil crearPerfil(PerfilCrearDTO dto);
    PerfilPublicoDTO obtenerPublico(Integer id);
    PerfilPublicoDTO editar(Integer id, PerfilEditarDTO dto);
    List<PropietariaDTO> afines(Integer idPerfil);
}