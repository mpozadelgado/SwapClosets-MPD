package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioEstilo;
import es.safareyes.swapclosetsmpd.dtos.EstiloCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Estilo;
import es.safareyes.swapclosetsmpd.servicios.IEstiloService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstiloServiceImpl implements IEstiloService {

    private final IRepositorioEstilo estiloRepository;

    public EstiloServiceImpl(IRepositorioEstilo estiloRepository) {
        this.estiloRepository = estiloRepository;
    }

    @Override
    @Transactional
    public Estilo crearEstilo(EstiloCrearDTO dto) throws Exception {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del estilo es obligatorio");
        }

        Estilo estilo = Estilo.builder()
                .nombre(dto.getNombre())
                .build();

        return estiloRepository.save(estilo);
    }
}