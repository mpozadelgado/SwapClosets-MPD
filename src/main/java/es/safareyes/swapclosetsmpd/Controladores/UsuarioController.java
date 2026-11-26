package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.PerfilEditarDTO;
import es.safareyes.swapclosetsmpd.dtos.PerfilPublicoDTO;
import es.safareyes.swapclosetsmpd.dtos.PropietariaDTO;
import es.safareyes.swapclosetsmpd.servicios.IPerfilService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private IPerfilService perfilService;

    @GetMapping("/afines")
    public List<PropietariaDTO> afines(@RequestParam Integer idPerfil) {
        return perfilService.afines(idPerfil);
    }

    @GetMapping("/{id}")
    public PerfilPublicoDTO obtener(@PathVariable Integer id) {
        return perfilService.obtenerPublico(id);
    }

    @PutMapping("/{id}")
    public PerfilPublicoDTO editar(@PathVariable Integer id, @Valid @RequestBody PerfilEditarDTO dto) {
        return perfilService.editar(id, dto);
    }
}