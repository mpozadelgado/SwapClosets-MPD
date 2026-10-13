package es.safareyes.swapclosetsmpd.servicios.impl;


import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioValoraciones;
import es.safareyes.swapclosetsmpd.dtos.ValoracionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import es.safareyes.swapclosetsmpd.servicios.IValoracionesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ValoracionesServiceImpl implements IValoracionesService{
    private final IRepositorioValoraciones valoracionesRepository;
    private final IRepositorioSolicitudes solicitudesRepository;
    private final IRepositorioPerfil perfilRepository;

    public ValoracionesServiceImpl(IRepositorioValoraciones valoracionesRepository,
                                   IRepositorioSolicitudes solicitudesRepository,
                                   IRepositorioPerfil perfilRepository) {
        this.valoracionesRepository = valoracionesRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    @Transactional
    public Valoraciones crearValoracion(ValoracionCrearDTO dto) throws Exception {
        if (dto.getPuntuacion() == null || dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new Exception("La puntuación debe estar entre 1 y 5");
        }

        Solicitudes solicitud = solicitudesRepository.findById(dto.getIdSolicitud())
                .orElseThrow(() -> new Exception("La solicitud no existe"));

        Perfil autor = perfilRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new Exception("El perfil autor no existe"));

        Perfil destinatario = perfilRepository.findById(dto.getIdDestinatario())
                .orElseThrow(() -> new Exception("El perfil destinatario no existe"));

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
}
