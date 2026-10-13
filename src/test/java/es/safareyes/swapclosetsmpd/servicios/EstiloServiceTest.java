package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioEstilo;
import es.safareyes.swapclosetsmpd.dtos.EstiloCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Estilo;
import es.safareyes.swapclosetsmpd.servicios.impl.EstiloServiceImpl;
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
class EstiloServiceTest {

    @InjectMocks
    private EstiloServiceImpl estiloService;

    @Mock
    private IRepositorioEstilo estiloRepository;

    @Test
    @DisplayName("Test 1: Crear estilo con nombre vacío lanza excepción")
    @Tag("Estilo")
    void testCrearEstiloNombreVacio() {
        EstiloCrearDTO dto = EstiloCrearDTO.builder().nombre("").build();

        Exception exception = assertThrows(Exception.class, () -> estiloService.crearEstilo(dto));
        assertEquals("El nombre del estilo es obligatorio", exception.getMessage());

        verifyNoInteractions(estiloRepository);
    }

    @Test
    @DisplayName("Test 2: Crear estilo con nombre válido lo guarda correctamente")
    @Tag("Estilo")
    void testCrearEstiloExitoso() throws Exception {
        EstiloCrearDTO dto = EstiloCrearDTO.builder().nombre("Casual").build();
        Estilo estiloGuardado = Estilo.builder().id(1).nombre("Casual").build();

        when(estiloRepository.save(any(Estilo.class))).thenReturn(estiloGuardado);

        Estilo resultado = estiloService.crearEstilo(dto);

        assertNotNull(resultado);
        assertEquals("Casual", resultado.getNombre());

        verify(estiloRepository).save(any(Estilo.class));
    }
}