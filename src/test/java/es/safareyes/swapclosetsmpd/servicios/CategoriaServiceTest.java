package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.dtos.CategoriaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Categoria;
import es.safareyes.swapclosetsmpd.servicios.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Mock
    private IRepositorioCategoria categoriaRepository;

    @Test
    @DisplayName("Test 1: Crear categoría con nombre vacío lanza excepción")
    @Tag("Categoria")
    void testCrearCategoriaNombreVacio() {
        CategoriaCrearDTO dto = CategoriaCrearDTO.builder().nombre("").build();

        Exception exception = assertThrows(Exception.class, () -> categoriaService.crearCategoria(dto));
        assertEquals("El nombre de la categoría es obligatorio", exception.getMessage());

        verifyNoInteractions(categoriaRepository);
    }

    @Test
    @DisplayName("Test 2: Crear categoría con nombre existente lanza excepción")
    @Tag("Categoria")
    void testCrearCategoriaNombreDuplicado() {
        CategoriaCrearDTO dto = CategoriaCrearDTO.builder().nombre("Camisetas").build();

        when(categoriaRepository.existsByNombreIgnoreCase("Camisetas")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> categoriaService.crearCategoria(dto));
        assertEquals("La categoría ya existe", exception.getMessage());

        verify(categoriaRepository).existsByNombreIgnoreCase("Camisetas");
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Crear categoría con datos válidos la guarda correctamente")
    @Tag("Categoria")
    void testCrearCategoriaExitoso() throws Exception {
        CategoriaCrearDTO dto = CategoriaCrearDTO.builder().nombre("Abrigos").build();
        Categoria categoriaGuardada = Categoria.builder().id(1).nombre("Abrigos").build();

        when(categoriaRepository.existsByNombreIgnoreCase("Abrigos")).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaGuardada);

        Categoria resultado = categoriaService.crearCategoria(dto);

        assertNotNull(resultado);
        assertEquals("Abrigos", resultado.getNombre());

        verify(categoriaRepository).existsByNombreIgnoreCase("Abrigos");
        verify(categoriaRepository).save(any(Categoria.class));
    }
}
