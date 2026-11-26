package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioFoto;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioValoraciones;
import es.safareyes.swapclosetsmpd.conversores.PrendaMapper;
import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaDetalleDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaTarjetaDTO;
import es.safareyes.swapclosetsmpd.dtos.PropietariaDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.modelos.Foto;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.servicios.IPrendaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrendaServiceImpl implements IPrendaService {

    private final IRepositorioPrenda prendaRepository;
    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioCategoria categoriaRepository;
    private final IRepositorioFoto fotoRepository;
    private final IRepositorioValoraciones valoracionesRepository;
    private final PrendaMapper mapper;

    public PrendaServiceImpl(IRepositorioPrenda prendaRepository,
                             IRepositorioPerfil perfilRepository,
                             IRepositorioCategoria categoriaRepository,
                             IRepositorioFoto fotoRepository,
                             IRepositorioValoraciones valoracionesRepository,
                             PrendaMapper mapper) {
        this.prendaRepository = prendaRepository;
        this.perfilRepository = perfilRepository;
        this.categoriaRepository = categoriaRepository;
        this.fotoRepository = fotoRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Prenda publicarPrenda(PrendaCrearDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new ReglaNegocioException("El título de la prenda es obligatorio");
        }

        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new ElementoNoEncontrado("El perfil asociado no existe"));

        Categoria categoria = null;
        if (dto.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new ElementoNoEncontrado("La categoría no existe"));
        }

        Prenda prenda = Prenda.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .talla(dto.getTalla())
                .estado(dto.getEstado())
                .disponibilidad(dto.getDisponibilidad() != null ? dto.getDisponibilidad() : true)
                .fechaPublicacion(LocalDate.now())
                .perfil(perfil)
                .categoria(categoria)
                .build();

        return prendaRepository.save(prenda);
    }

    @Override
    @Transactional
    public PrendaDetalleDTO crear(PrendaCrearDTO dto) {
        return aDetalle(publicarPrenda(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public PrendaDetalleDTO obtener(Integer id) {
        Prenda prenda = prendaRepository.findByIdConDetalles(id)
                .orElseThrow(() -> new ElementoNoEncontrado("No existe la prenda con id " + id));
        return aDetalle(prenda);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrendaTarjetaDTO> buscar(String texto, Integer idCategoria, String talla, int page, int size) {
        Specification<Prenda> filtro = (root, query, cb) -> cb.isTrue(root.get("disponibilidad"));
        if (texto != null && !texto.isBlank()) {
            filtro = filtro.and((root, query, cb) -> cb.like(cb.lower(root.get("titulo")), "%" + texto.toLowerCase() + "%"));
        }
        if (idCategoria != null) {
            filtro = filtro.and((root, query, cb) -> cb.equal(root.get("categoria").get("id"), idCategoria));
        }
        if (talla != null && !talla.isBlank()) {
            filtro = filtro.and((root, query, cb) -> cb.equal(root.get("talla"), talla));
        }

        PageRequest pagina = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());
        return prendaRepository.findAll(filtro, pagina)
                .map(p -> mapper.toTarjeta(p, primeraFoto(p.getId())));
    }

    private PrendaDetalleDTO aDetalle(Prenda prenda) {
        List<String> fotos = fotoRepository.findByPrendaIdOrderByOrdenAsc(prenda.getId())
                .stream().map(Foto::getUrl).toList();
        return mapper.toDetalle(prenda, fotos, aPropietaria(prenda.getPerfil()));
    }

    private PropietariaDTO aPropietaria(Perfil perfil) {
        Double media = valoracionesRepository.obtenerPuntuacionMediaPerfil(perfil.getId());
        long total = valoracionesRepository.countByDestinatarioId(perfil.getId());
        double reputacion = media == null ? 0.0 : Math.round(media * 10) / 10.0;
        return new PropietariaDTO(perfil.getId(), perfil.getNombre(), perfil.getApellido1(), reputacion, total);
    }

    private String primeraFoto(Integer idPrenda) {
        return fotoRepository.findByPrendaIdOrderByOrdenAsc(idPrenda).stream()
                .findFirst().map(Foto::getUrl).orElse(null);
    }
}