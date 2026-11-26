package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.MensajeDTO;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import org.springframework.data.domain.Page;

public interface IMensajesService {
    Mensajes enviarMensaje(MensajeCrearDTO dto);
    MensajeDTO enviarEnSolicitud(Integer idSolicitud, MensajeCrearDTO dto);
    Page<MensajeDTO> conversacion(Integer idSolicitud, Integer idPerfil, int page, int size);
}