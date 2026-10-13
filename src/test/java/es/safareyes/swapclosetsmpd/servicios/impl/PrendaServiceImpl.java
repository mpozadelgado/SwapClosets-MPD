package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.servicios.IPrendaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@Service
public class PrendaServiceImpl implements IPrendaService{

    private final IRepositorioPrenda prendaRepository;
    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioCategoria categoriaRepository;

    public PrendaServiceImpl(IRepositorioPrenda prendaRepository,
                             IRepositorioPerfil perfilRepository,
                             IRepositorioCategoria categoriaRepository) {
        this.prendaRepository = prendaRepository;
        this.perfilRepository = perfilRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public Prenda publicarPrenda(PrendaCrearDTO dto) throws Exception {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new Exception("El título de la prenda es obligatorio");
        }

        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new Exception("El perfil asociado no existe"));

        Categoria categoria = null;
        if (dto.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);
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
}
