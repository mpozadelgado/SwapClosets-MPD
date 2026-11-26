package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.CambioEstadoDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudDTO;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;

import java.util.List;

public interface ISolicitudesService {
    Solicitudes crearSolicitud(SolicitudCrearDTO dto);
    SolicitudDTO crear(SolicitudCrearDTO dto);
    List<SolicitudDTO> listar(Integer idPerfil, String bandeja, String estado);
    SolicitudDTO cambiarEstado(Integer id, CambioEstadoDTO dto);
}