package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioEstilo;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioFoto;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioUsuario;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioValoraciones;
import es.safareyes.swapclosetsmpd.conversores.PerfilMapper;
import es.safareyes.swapclosetsmpd.conversores.PrendaMapper;
import es.safareyes.swapclosetsmpd.dtos.PerfilCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.PerfilEditarDTO;
import es.safareyes.swapclosetsmpd.dtos.PerfilPublicoDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaTarjetaDTO;
import es.safareyes.swapclosetsmpd.dtos.PropietariaDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Estilo;
import es.safareyes.swapclosetsmpd.modelos.Foto;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Usuario;
import es.safareyes.swapclosetsmpd.servicios.IPerfilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilServiceImpl implements IPerfilService {

    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioUsuario usuarioRepository;
    private final IRepositorioEstilo estiloRepository;
    private final IRepositorioPrenda prendaRepository;
    private final IRepositorioFoto fotoRepository;
    private final IRepositorioValoraciones valoracionesRepository;
    private final PerfilMapper perfilMapper;
    private final PrendaMapper prendaMapper;

    public PerfilServiceImpl(IRepositorioPerfil perfilRepository,
                             IRepositorioUsuario usuarioRepository,
                             IRepositorioEstilo estiloRepository,
                             IRepositorioPrenda prendaRepository,
                             IRepositorioFoto fotoRepository,
                             IRepositorioValoraciones valoracionesRepository,
                             PerfilMapper perfilMapper,
                             PrendaMapper prendaMapper) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.estiloRepository = estiloRepository;
        this.prendaRepository = prendaRepository;
        this.fotoRepository = fotoRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.perfilMapper = perfilMapper;
        this.prendaMapper = prendaMapper;
    }

    @Override
    @Transactional
    public Perfil crearPerfil(PerfilCrearDTO dto) {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new ReglaNegocioException("El email del perfil no es válido");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ElementoNoEncontrado("El usuario no existe"));

        Perfil perfil = Perfil.builder()
                .nombre(dto.getNombre())
                .apellido1(dto.getApellido1())
                .apellido2(dto.getApellido2())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .biografia(dto.getBiografia())
                .talla(dto.getTalla())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
    }

    @Override
    @Transactional(readOnly = true)
    public PerfilPublicoDTO obtenerPublico(Integer id) {
        return aPublico(buscarPerfil(id));
    }

    @Override
    @Transactional
    public PerfilPublicoDTO editar(Integer id, PerfilEditarDTO dto) {
        Perfil perfil = buscarPerfil(id);

        List<Integer> ids = dto.idsEstilos().stream().distinct().toList();
        List<Estilo> estilos = estiloRepository.findAllById(ids);
        if (estilos.size() != ids.size()) {
            throw new ElementoNoEncontrado("Alguno de los estilos no existe");
        }

        perfilMapper.editarDatos(perfil, dto);
        perfil.setEstilos(new ArrayList<>(estilos));

        return aPublico(perfilRepository.save(perfil));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropietariaDTO> afines(Integer idPerfil) {
        Perfil perfil = buscarPerfil(idPerfil);
        List<Integer> misEstilos = perfil.getEstilos().stream().map(Estilo::getId).toList();
        if (misEstilos.isEmpty()) {
            return List.of();
        }
        return perfilRepository.findPerfilesAfines(misEstilos, idPerfil).stream()
                .map(this::aResumen)
                .toList();
    }

    private Perfil buscarPerfil(Integer id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontrado("No existe el perfil con id " + id));
    }

    private PerfilPublicoDTO aPublico(Perfil perfil) {
        PropietariaDTO resumen = aResumen(perfil);
        List<String> estilos = perfil.getEstilos() == null ? List.of()
                : perfil.getEstilos().stream().map(Estilo::getNombre).toList();
        List<PrendaTarjetaDTO> prendas = prendaRepository.findByPerfilIdAndDisponibilidadTrue(perfil.getId()).stream()
                .map(p -> prendaMapper.toTarjeta(p, fotoRepository.findByPrendaIdOrderByOrdenAsc(p.getId()).stream()
                        .findFirst().map(Foto::getUrl).orElse(null)))
                .toList();
        return new PerfilPublicoDTO(perfil.getId(), perfil.getNombre(), perfil.getApellido1(),
                perfil.getBiografia(), perfil.getTalla(), estilos,
                resumen.reputacion(), resumen.valoraciones(), prendas);
    }

    private PropietariaDTO aResumen(Perfil perfil) {
        Double media = valoracionesRepository.obtenerPuntuacionMediaPerfil(perfil.getId());
        long total = valoracionesRepository.countByDestinatarioId(perfil.getId());
        double reputacion = media == null ? 0.0 : Math.round(media * 10) / 10.0;
        return new PropietariaDTO(perfil.getId(), perfil.getNombre(), perfil.getApellido1(), reputacion, total);
    }
}