package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.UsuarioRegistroDTO;
import es.safareyes.swapclosetsmpd.modelos.Usuario;

public interface IUsuarioService {

    Usuario registrarUsuario(UsuarioRegistroDTO dto) throws Exception;
}
