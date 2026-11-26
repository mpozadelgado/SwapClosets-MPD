package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.MensajeDTO;
import es.safareyes.swapclosetsmpd.dtos.ValoracionCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.ValoracionDTO;
import es.safareyes.swapclosetsmpd.servicios.IMensajesService;
import es.safareyes.swapclosetsmpd.servicios.IValoracionesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solicitudes")
@AllArgsConstructor
public class ConversacionController {

    private IMensajesService mensajesService;
    private IValoracionesService valoracionesService;

    @PostMapping("/{id}/mensajes")
    @ResponseStatus(HttpStatus.CREATED)
    public MensajeDTO enviar(@PathVariable Integer id, @Valid @RequestBody MensajeCrearDTO dto) {
        return mensajesService.enviarEnSolicitud(id, dto);
    }

    @GetMapping("/{id}/mensajes")
    public Page<MensajeDTO> conversacion(@PathVariable Integer id,
                                         @RequestParam Integer idPerfil,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return mensajesService.conversacion(id, idPerfil, page, size);
    }

    @PostMapping("/{id}/valoraciones")
    @ResponseStatus(HttpStatus.CREATED)
    public ValoracionDTO valorar(@PathVariable Integer id, @Valid @RequestBody ValoracionCrearDTO dto) {
        return valoracionesService.valorar(id, dto);
    }
}