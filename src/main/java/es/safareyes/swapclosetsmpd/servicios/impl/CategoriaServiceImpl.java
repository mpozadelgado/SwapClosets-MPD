package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.conversores.CategoriaMapper;
import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.CategoriaDTO;
import es.safareyes.swapclosetsmpd.excepciones.ElementoDuplicado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.servicios.ICategoriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    private final IRepositorioCategoria categoriaRepository;
    private final IRepositorioPrenda prendaRepository;
    private final CategoriaMapper mapper;

    public CategoriaServiceImpl(IRepositorioCategoria categoriaRepository, IRepositorioPrenda prendaRepository, CategoriaMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.prendaRepository = prendaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Categoria crearCategoria(CategoriaCrearDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre de la categoría es obligatorio");
        }

        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ElementoDuplicado("La categoría ya existe");
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .build();

        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(c -> mapper.toDTO(c, prendaRepository.countByCategoriaIdAndDisponibilidadTrue(c.getId())))
                .toList();
    }
}