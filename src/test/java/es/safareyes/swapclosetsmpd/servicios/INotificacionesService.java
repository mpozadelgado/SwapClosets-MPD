package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.NotificacionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Notificaciones;

public interface INotificacionesService {
    Notificaciones crearNotificacion(NotificacionCrearDTO dto) throws Exception;
}
