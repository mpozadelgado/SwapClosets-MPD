package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.dtos.CategoriaDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.servicios.ICategoriaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@AllArgsConstructor
public class CategoriaController {

    private ICategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listar() {
        return categoriaService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria crear(@Valid @RequestBody CategoriaCrearDTO dto) {
        return categoriaService.crearCategoria(dto);
    }
}