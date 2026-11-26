package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioMensajes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.conversores.MensajeMapper;
import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.MensajeDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.servicios.IMensajesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MensajesServiceImpl implements IMensajesService {

    private final IRepositorioMensajes mensajesRepository;
    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioSolicitudes solicitudesRepository;
    private final MensajeMapper mapper;

    public MensajesServiceImpl(IRepositorioMensajes mensajesRepository,
                               IRepositorioPerfil perfilRepository,
                               IRepositorioSolicitudes solicitudesRepository,
                               MensajeMapper mapper) {
        this.mensajesRepository = mensajesRepository;
        this.perfilRepository = perfilRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Mensajes enviarMensaje(MensajeCrearDTO dto) {
        if (dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
            throw new ReglaNegocioException("El contenido del mensaje no puede estar vacío");
        }

        Perfil emisor = perfilRepository.findById(dto.getIdEmisor())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil emisor no existe"));

        Perfil receptor = perfilRepository.findById(dto.getIdReceptor())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil receptor no existe"));

        Mensajes mensaje = Mensajes.builder()
                .contenido(dto.getContenido())
                .fechaEnvio(LocalDateTime.now())
                .horaEnvio(LocalTime.now())
                .leido(false)
                .emisor(emisor)
                .receptor(receptor)
                .build();

        return mensajesRepository.save(mensaje);
    }

    @Override
    @Transactional
    public MensajeDTO enviarEnSolicitud(Integer idSolicitud, MensajeCrearDTO dto) {
        Solicitudes solicitud = buscarSolicitud(idSolicitud);
        Integer otra = otraPersona(solicitud, dto.getIdEmisor());

        if (List.of("RECHAZADA", "CANCELADA", "CADUCADA").contains(solicitud.getEstado())) {
            throw new ReglaNegocioException("No se puede escribir en una solicitud " + solicitud.getEstado().toLowerCase());
        }

        dto.setIdReceptor(otra);
        Mensajes mensaje = enviarMensaje(dto);
        mensaje.setSolicitud(solicitud);
        return mapper.toDTO(mensajesRepository.save(mensaje));
    }

    @Override
    @Transactional
    public Page<MensajeDTO> conversacion(Integer idSolicitud, Integer idPerfil, int page, int size) {
        Solicitudes solicitud = buscarSolicitud(idSolicitud);
        otraPersona(solicitud, idPerfil);

        mensajesRepository.marcarMensajesComoLeidos(idSolicitud, idPerfil);
        return mensajesRepository.findBySolicitudIdOrderByFechaEnvioAsc(idSolicitud, PageRequest.of(page, size))
                .map(mapper::toDTO);
    }

    private Solicitudes buscarSolicitud(Integer id) {
        return solicitudesRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontrado("No existe la solicitud con id " + id));
    }

    private Integer otraPersona(Solicitudes solicitud, Integer idPerfil) {
        Integer solicitante = solicitud.getPerfil().getId();
        Integer propietaria = solicitud.getPrenda().getPerfil().getId();
        if (idPerfil.equals(solicitante)) {
            return propietaria;
        }
        if (idPerfil.equals(propietaria)) {
            return solicitante;
        }
        throw new ReglaNegocioException("No participas en esta solicitud");
    }
}