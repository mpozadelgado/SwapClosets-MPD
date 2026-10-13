package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioNotificaciones;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.dtos.NotificacionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Notificaciones;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.servicios.INotificacionesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class NotificacionesServiceImpl implements INotificacionesService {

    private final IRepositorioNotificaciones notificacionesRepository;
    private final IRepositorioPerfil perfilRepository;

    public NotificacionesServiceImpl(IRepositorioNotificaciones notificacionesRepository,
                                     IRepositorioPerfil perfilRepository) {
        this.notificacionesRepository = notificacionesRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    @Transactional
    public Notificaciones crearNotificacion(NotificacionCrearDTO dto) throws Exception {
        if (dto.getMensaje() == null || dto.getMensaje().trim().isEmpty()) {
            throw new Exception("El mensaje de la notificación no puede estar vacío");
        }

        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new Exception("El perfil no existe"));

        Notificaciones notificacion = Notificaciones.builder()
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo() != null ? dto.getTipo() : "SISTEMA")
                .leido(false)
                .fechaCreacion(LocalDateTime.now())
                .perfil(perfil)
                .build();

        return notificacionesRepository.save(notificacion);
    }
}
