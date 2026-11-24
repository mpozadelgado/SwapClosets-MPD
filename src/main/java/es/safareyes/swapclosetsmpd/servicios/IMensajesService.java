package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;

public interface IMensajesService {
    Mensajes enviarMensaje(MensajeCrearDTO dto) throws Exception;
}
