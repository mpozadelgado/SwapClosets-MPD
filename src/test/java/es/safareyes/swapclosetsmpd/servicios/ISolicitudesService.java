package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;

public interface ISolicitudesService {
    Solicitudes crearSolicitud(SolicitudCrearDTO dto) throws Exception;

}
