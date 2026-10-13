package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.servicios.ISolicitudesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class SolicitudesServiceImpl implements ISolicitudesService{

    private final IRepositorioSolicitudes solicitudesRepository;
    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioPrenda prendaRepository;

    public SolicitudesServiceImpl(IRepositorioSolicitudes solicitudesRepository,
                                  IRepositorioPerfil perfilRepository,
                                  IRepositorioPrenda prendaRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.perfilRepository = perfilRepository;
        this.prendaRepository = prendaRepository;
    }

    @Override
    @Transactional
    public Solicitudes crearSolicitud(SolicitudCrearDTO dto) throws Exception {
        if (dto.getTiposolicitud() == null || dto.getTiposolicitud().trim().isEmpty()) {
            throw new Exception("El tipo de solicitud es obligatorio");
        }

        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new Exception("El perfil solicitante no existe"));

        Prenda prenda = prendaRepository.findById(dto.getIdPrenda())
                .orElseThrow(() -> new Exception("La prenda solicitada no existe"));

        Solicitudes solicitud = Solicitudes.builder()
                .tiposolicitud(dto.getTiposolicitud())
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .fechaFin(dto.getFechafin())
                .perfil(perfil)
                .prenda(prenda)
                .build();

        return solicitudesRepository.save(solicitud);
    }
}
