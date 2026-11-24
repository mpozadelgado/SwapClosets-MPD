package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.PerfilCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;

public interface IPerfilService {
    Perfil crearPerfil(PerfilCrearDTO dto) throws Exception;
}
