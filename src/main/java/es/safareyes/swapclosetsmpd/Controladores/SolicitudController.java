package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.CambioEstadoDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.SolicitudDTO;
import es.safareyes.swapclosetsmpd.servicios.ISolicitudesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes")
@AllArgsConstructor
public class SolicitudController {

    private ISolicitudesService solicitudesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudDTO crear(@Valid @RequestBody SolicitudCrearDTO dto) {
        return solicitudesService.crear(dto);
    }

    @GetMapping
    public List<SolicitudDTO> listar(@RequestParam Integer idPerfil,
                                     @RequestParam(defaultValue = "recibidas") String bandeja,
                                     @RequestParam(required = false) String estado) {
        return solicitudesService.listar(idPerfil, bandeja, estado);
    }

    @PatchMapping("/{id}/estado")
    public SolicitudDTO cambiarEstado(@PathVariable Integer id, @Valid @RequestBody CambioEstadoDTO dto) {
        return solicitudesService.cambiarEstado(id, dto);
    }
}