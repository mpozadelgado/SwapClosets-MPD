package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.ActividadCategoriaDTO;
import es.safareyes.swapclosetsmpd.servicios.InformeService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/informes")
@AllArgsConstructor
public class InformeController {

    private InformeService informeService;

    @GetMapping("/actividad")
    public List<ActividadCategoriaDTO> actividad(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return informeService.actividad(desde, hasta);
    }
}