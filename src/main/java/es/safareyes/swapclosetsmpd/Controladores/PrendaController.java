package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaDetalleDTO;
import es.safareyes.swapclosetsmpd.dtos.PrendaTarjetaDTO;
import es.safareyes.swapclosetsmpd.servicios.IPrendaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prendas")
@AllArgsConstructor
public class PrendaController {

    private IPrendaService prendaService;

    @GetMapping
    public Page<PrendaTarjetaDTO> buscar(@RequestParam(required = false) String texto,
                                         @RequestParam(required = false) Integer idCategoria,
                                         @RequestParam(required = false) String talla,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return prendaService.buscar(texto, idCategoria, talla, page, size);
    }

    @GetMapping("/{id}")
    public PrendaDetalleDTO obtener(@PathVariable Integer id) {
        return prendaService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrendaDetalleDTO crear(@Valid @RequestBody PrendaCrearDTO dto) {
        return prendaService.crear(dto);
    }

    @PutMapping("/{id}")
    public PrendaDetalleDTO editar(@PathVariable Integer id, @Valid @RequestBody PrendaCrearDTO dto) {
        return prendaService.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirar(@PathVariable Integer id) {
        prendaService.retirar(id);
    }
}