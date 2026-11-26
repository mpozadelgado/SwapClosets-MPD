package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.conversores.SolicitudMapper;
import es.safareyes.swapclosetsmpd.dtos.CambioEstadoDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoDuplicado;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.servicios.ISolicitudesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudesServiceImpl implements ISolicitudesService {

    private final IRepositorioSolicitudes solicitudesRepository;
    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioPrenda prendaRepository;
    private final SolicitudMapper mapper;

    public SolicitudesServiceImpl(IRepositorioSolicitudes solicitudesRepository,
                                  IRepositorioPerfil perfilRepository,
                                  IRepositorioPrenda prendaRepository,
                                  SolicitudMapper mapper) {
        this.solicitudesRepository = solicitudesRepository;
        this.perfilRepository = perfilRepository;
        this.prendaRepository = prendaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Solicitudes crearSolicitud(SolicitudCrearDTO dto) {
        if (dto.getTiposolicitud() == null || dto.getTiposolicitud().trim().isEmpty()) {
            throw new ReglaNegocioException("El tipo de solicitud es obligatorio");
        }

        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil solicitante no existe"));

        Prenda prenda = prendaRepository.findById(dto.getIdPrenda())
                .orElseThrow(() -> new ElementoNoEncontrado("La prenda solicitada no existe"));

        String tipo = dto.getTiposolicitud().toUpperCase();
        if (!tipo.equals("INTERCAMBIO") && !tipo.equals("PRESTAMO")) {
            throw new ReglaNegocioException("El tipo debe ser INTERCAMBIO o PRESTAMO");
        }

        if (prenda.getPerfil() != null && prenda.getPerfil().getId().equals(perfil.getId())) {
            throw new ReglaNegocioException("No puedes solicitar una prenda propia");
        }

        if (Boolean.FALSE.equals(prenda.getDisponibilidad())) {
            throw new ReglaNegocioException("La prenda no está disponible");
        }

        if (solicitudesRepository.existeSolicitudActiva(perfil.getId(), prenda.getId(), "PENDIENTE")) {
            throw new ElementoDuplicado("Ya tienes una solicitud pendiente de esta prenda");
        }

        Prenda ofrecida = null;
        if (tipo.equals("INTERCAMBIO") && dto.getIdPrendaOfrecida() != null) {
            ofrecida = prendaRepository.findById(dto.getIdPrendaOfrecida())
                    .orElseThrow(() -> new ElementoNoEncontrado("La prenda ofrecida no existe"));
            if (ofrecida.getPerfil() == null || !ofrecida.getPerfil().getId().equals(perfil.getId())) {
                throw new ReglaNegocioException("La prenda ofrecida tiene que ser tuya");
            }
            if (Boolean.FALSE.equals(ofrecida.getDisponibilidad())) {
                throw new ReglaNegocioException("La prenda ofrecida no está disponible");
            }
        }

        if (tipo.equals("PRESTAMO")) {
            LocalDate fin = dto.getFechafin();
            if (fin == null) {
                throw new ReglaNegocioException("Un préstamo necesita fecha de fin");
            }
            if (!fin.isAfter(LocalDate.now())) {
                throw new ReglaNegocioException("La fecha de fin tiene que ser posterior a hoy");
            }
            if (fin.isAfter(LocalDate.now().plusDays(30))) {
                throw new ReglaNegocioException("Un préstamo dura como máximo 30 días");
            }
            long activos = solicitudesRepository.contarPrestamosActivos(perfil.getId(), "PRESTAMO",
                    List.of("PENDIENTE", "ACEPTADA", "RETRASADA"));
            if (activos >= 3) {
                throw new ReglaNegocioException("No puedes tener más de 3 préstamos activos");
            }
        }

        Solicitudes solicitud = Solicitudes.builder()
                .tiposolicitud(tipo)
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .fechaFin(dto.getFechafin())
                .perfil(perfil)
                .prenda(prenda)
                .prendaOfrecida(ofrecida)
                .build();

        return solicitudesRepository.save(solicitud);
    }

    @Override
    @Transactional
    public SolicitudDTO crear(SolicitudCrearDTO dto) {
        return mapper.toDTO(crearSolicitud(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listar(Integer idPerfil, String bandeja, String estado) {
        List<Solicitudes> lista = "enviadas".equalsIgnoreCase(bandeja)
                ? solicitudesRepository.findByPerfilIdOrderByFechaCreacionDesc(idPerfil)
                : solicitudesRepository.findByPrendaPerfilIdOrderByFechaCreacionDesc(idPerfil);

        return lista.stream()
                .filter(s -> estado == null || estado.isBlank() || s.getEstado().equalsIgnoreCase(estado))
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public SolicitudDTO cambiarEstado(Integer id, CambioEstadoDTO dto) {
        Solicitudes solicitud = solicitudesRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontrado("No existe la solicitud con id " + id));

        String nuevo = dto.estado().toUpperCase();
        String actual = solicitud.getEstado();
        Integer propietaria = solicitud.getPrenda().getPerfil().getId();
        Integer solicitante = solicitud.getPerfil().getId();
        Integer quien = dto.idPerfil();

        if (!quien.equals(propietaria) && !quien.equals(solicitante)) {
            throw new ReglaNegocioException("No participas en esta solicitud");
        }

        switch (nuevo) {
            case "ACEPTADA", "RECHAZADA" -> {
                comprobar(quien.equals(propietaria), "Solo la propietaria puede aceptar o rechazar");
                comprobar(actual.equals("PENDIENTE"), "Solo se puede responder a una solicitud pendiente");
            }
            case "CANCELADA" -> {
                comprobar(quien.equals(solicitante), "Solo quien la envió puede cancelarla");
                comprobar(actual.equals("PENDIENTE"), "Solo se puede cancelar una solicitud pendiente");
            }
            case "FINALIZADA" -> {
                comprobar(quien.equals(propietaria), "Solo la propietaria puede finalizarla");
                comprobar(actual.equals("ACEPTADA") || actual.equals("RETRASADA"),
                        "Solo se puede finalizar una solicitud aceptada o retrasada");
            }
            default -> throw new ReglaNegocioException("No se puede pasar de " + actual + " a " + nuevo);
        }

        if (nuevo.equals("ACEPTADA")) {
            solicitudesRepository.rechazarDemasSolicitudesPendientes(solicitud.getPrenda().getId(), id);
        }

        Prenda prenda = solicitud.getPrenda();
        Prenda ofrecida = solicitud.getPrendaOfrecida();

        if (nuevo.equals("ACEPTADA")) {
            prenda.setDisponibilidad(false);
            if (ofrecida != null) {
                ofrecida.setDisponibilidad(false);
            }
        }

        if (nuevo.equals("FINALIZADA")) {
            if (solicitud.getTiposolicitud().equals("INTERCAMBIO") && ofrecida != null) {
                Perfil duenaPrenda = prenda.getPerfil();
                prenda.setPerfil(ofrecida.getPerfil());
                ofrecida.setPerfil(duenaPrenda);
                ofrecida.setDisponibilidad(true);
            }
            prenda.setDisponibilidad(true);
        }

        if (nuevo.equals("ACEPTADA") || nuevo.equals("RECHAZADA")) {
            solicitud.setFechaRespuesta(LocalDateTime.now());
        }
        solicitud.setEstado(nuevo);

        prendaRepository.save(prenda);
        if (ofrecida != null) {
            prendaRepository.save(ofrecida);
        }
        return mapper.toDTO(solicitudesRepository.save(solicitud));
    }

    private void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new ReglaNegocioException(mensaje);
        }
    }
}