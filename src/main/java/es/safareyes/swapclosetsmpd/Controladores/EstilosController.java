package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.EstiloCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Estilo;
import es.safareyes.swapclosetsmpd.servicios.IEstiloService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estilos")
@AllArgsConstructor
public class EstilosController {

    private IEstiloService estiloService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estilo crear(@RequestBody EstiloCrearDTO dto) throws Exception {
        return estiloService.crearEstilo(dto);
    }
}