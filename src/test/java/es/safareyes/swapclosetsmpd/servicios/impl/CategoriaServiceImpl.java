package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.servicios.ICategoriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    private final IRepositorioCategoria categoriaRepository;

    public CategoriaServiceImpl(IRepositorioCategoria categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public Categoria crearCategoria(CategoriaCrearDTO dto) throws Exception {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre de la categoría es obligatorio");
        }

        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new Exception("La categoría ya existe");
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .build();

        return categoriaRepository.save(categoria);
    }
}
