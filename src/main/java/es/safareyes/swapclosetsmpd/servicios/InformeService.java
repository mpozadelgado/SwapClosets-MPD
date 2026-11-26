package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.dtos.ActividadCategoriaDTO;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InformeService {

    private IRepositorioPrenda prendaRepository;

    @Transactional(readOnly = true)
    public List<ActividadCategoriaDTO> actividad(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new ReglaNegocioException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        return prendaRepository.actividadPorCategoriaYMes(desde, hasta).stream()
                .map(a -> new ActividadCategoriaDTO(a.getCategoria(), a.getMes(),
                        a.getPublicaciones(), a.getIntercambios(), a.getPrestamos()))
                .toList();
    }
}