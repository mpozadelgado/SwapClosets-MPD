package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioCategoria;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.dtos.PrendaCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.servicios.impl.PrendaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrendaServiceTest {

    @InjectMocks
    private PrendaServiceImpl prendaService;

    @Mock
    private IRepositorioPrenda prendaRepository;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Mock
    private IRepositorioCategoria categoriaRepository;

    @Test
    @DisplayName("Test 1: Publicar prenda con título vacío lanza excepción")
    @Tag("Prenda")
    void testPublicarPrendaTituloVacio() {
        PrendaCrearDTO dto = PrendaCrearDTO.builder()
                .titulo("")
                .idPerfil(1)
                .build();

        Exception exception = assertThrows(Exception.class, () -> prendaService.publicarPrenda(dto));
        assertEquals("El título de la prenda es obligatorio", exception.getMessage());

        verifyNoInteractions(prendaRepository);
    }

    @Test
    @DisplayName("Test 2: Publicar prenda con perfil inexistente lanza excepción")
    @Tag("Prenda")
    void testPublicarPrendaPerfilNoEncontrado() {
        PrendaCrearDTO dto = PrendaCrearDTO.builder()
                .titulo("Chaqueta de Cuero")
                .idPerfil(99)
                .build();

        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> prendaService.publicarPrenda(dto));
        assertEquals("El perfil asociado no existe", exception.getMessage());

        verify(prendaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Publicar prenda con datos válidos la guarda correctamente")
    @Tag("Prenda")
    void testPublicarPrendaExitoso() throws Exception {
        PrendaCrearDTO dto = PrendaCrearDTO.builder()
                .titulo("Chaqueta de Cuero")
                .descripcion("En perfecto estado, talla M")
                .talla("M")
                .estado("Usado - Como nuevo")
                .disponibilidad(true)
                .idPerfil(1)
                .build();

        Perfil perfilMock = Perfil.builder().id(1).nombre("Manuel").build();
        Prenda prendaGuardada = Prenda.builder()
                .id(100)
                .titulo("Chaqueta de Cuero")
                .talla("M")
                .perfil(perfilMock)
                .build();

        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfilMock));
        when(prendaRepository.save(any(Prenda.class))).thenReturn(prendaGuardada);

        Prenda resultado = prendaService.publicarPrenda(dto);

        assertNotNull(resultado);
        assertEquals("Chaqueta de Cuero", resultado.getTitulo());
        assertEquals("M", resultado.getTalla());

        verify(perfilRepository).findById(1);
        verify(prendaRepository).save(any(Prenda.class));
    }
}
