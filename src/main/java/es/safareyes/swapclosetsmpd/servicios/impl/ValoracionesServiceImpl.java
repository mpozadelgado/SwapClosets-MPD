package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioValoraciones;
import es.safareyes.swapclosetsmpd.conversores.ValoracionMapper;
import es.safareyes.swapclosetsmpd.dtos.ValoracionCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.ValoracionDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoDuplicado;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import es.safareyes.swapclosetsmpd.servicios.IValoracionesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ValoracionesServiceImpl implements IValoracionesService {

    private final IRepositorioValoraciones valoracionesRepository;
    private final IRepositorioSolicitudes solicitudesRepository;
    private final IRepositorioPerfil perfilRepository;
    private final ValoracionMapper mapper;

    public ValoracionesServiceImpl(IRepositorioValoraciones valoracionesRepository,
                                   IRepositorioSolicitudes solicitudesRepository,
                                   IRepositorioPerfil perfilRepository,
                                   ValoracionMapper mapper) {
        this.valoracionesRepository = valoracionesRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.perfilRepository = perfilRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Valoraciones crearValoracion(ValoracionCrearDTO dto) {
        if (dto.getPuntuacion() == null || dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new ReglaNegocioException("La puntuación debe estar entre 1 y 5");
        }

        Solicitudes solicitud = solicitudesRepository.findById(dto.getIdSolicitud())
                .orElseThrow(() -> new ElementoNoEncontrado("La solicitud no existe"));

        Perfil autor = perfilRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil autor no existe"));

        Perfil destinatario = perfilRepository.findById(dto.getIdDestinatario())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil destinatario no existe"));

        Valoraciones valoracion = Valoraciones.builder()
                .puntuacion(dto.getPuntuacion())
                .comentario(dto.getComentario())
                .fechaCreacion(LocalDateTime.now())
                .solicitud(solicitud)
                .autor(autor)
                .destinatario(destinatario)
                .build();

        return valoracionesRepository.save(valoracion);
    }

    @Override
    @Transactional
    public ValoracionDTO valorar(Integer idSolicitud, ValoracionCrearDTO dto) {
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud)
                .orElseThrow(() -> new ElementoNoEncontrado("No existe la solicitud con id " + idSolicitud));

        if (!"FINALIZADA".equals(solicitud.getEstado())) {
            throw new ReglaNegocioException("Solo se puede valorar una solicitud finalizada");
        }

        Integer solicitante = solicitud.getPerfil().getId();
        Integer propietaria = solicitud.getPrenda().getPerfil().getId();
        Integer autor = dto.getIdAutor();

        if (!autor.equals(solicitante) && !autor.equals(propietaria)) {
            throw new ReglaNegocioException("No participas en esta solicitud");
        }

        if (valoracionesRepository.existsBySolicitudIdAndAutorId(idSolicitud, autor)) {
            throw new ElementoDuplicado("Ya has valorado esta solicitud");
        }

        dto.setIdSolicitud(idSolicitud);
        dto.setIdDestinatario(autor.equals(solicitante) ? propietaria : solicitante);
        return mapper.toDTO(crearValoracion(dto));
    }
}