package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioMensajes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.servicios.IMensajesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class MensajesServiceImpl implements IMensajesService {

    private final IRepositorioMensajes mensajesRepository;
    private final IRepositorioPerfil perfilRepository;

    public MensajesServiceImpl(IRepositorioMensajes mensajesRepository, IRepositorioPerfil perfilRepository) {
        this.mensajesRepository = mensajesRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    @Transactional
    public Mensajes enviarMensaje(MensajeCrearDTO dto) throws Exception {
        if (dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
            throw new Exception("El contenido del mensaje no puede estar vacío");
        }

        Perfil emisor = perfilRepository.findById(dto.getIdEmisor())
                .orElseThrow(() -> new Exception("El perfil emisor no existe"));

        Perfil receptor = perfilRepository.findById(dto.getIdReceptor())
                .orElseThrow(() -> new Exception("El perfil receptor no existe"));

        Mensajes mensaje = Mensajes.builder()
                .contenido(dto.getContenido())
                .fechaEnvio(LocalDateTime.now())
                .horaEnvio(LocalTime.now())
                .emisor(emisor)
                .receptor(receptor)
                .build();

        return mensajesRepository.save(mensaje);
    }
}
