package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioValoraciones;
import es.safareyes.swapclosetsmpd.dtos.ValoracionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.modelos.Valoraciones;
import es.safareyes.swapclosetsmpd.servicios.impl.ValoracionesServiceImpl;
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
public class ValoracionesServiceTest {
    @InjectMocks
    private ValoracionesServiceImpl valoracionesService;

    @Mock
    private IRepositorioValoraciones valoracionesRepository;

    @Mock
    private IRepositorioSolicitudes solicitudesRepository;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Test
    @DisplayName("Test 1: Puntuación fuera de rango lanza excepción")
    @Tag("Valoraciones")
    void testCrearValoracionPuntuacionInvalida() {
        ValoracionCrearDTO dto = ValoracionCrearDTO.builder()
                .puntuacion(6)
                .idSolicitud(1)
                .idAutor(1)
                .idDestinatario(2)
                .build();

        Exception exception = assertThrows(Exception.class, () -> valoracionesService.crearValoracion(dto));
        assertEquals("La puntuación debe estar entre 1 y 5", exception.getMessage());

        verifyNoInteractions(valoracionesRepository);
    }

    @Test
    @DisplayName("Test 2: Crear valoración con solicitud no encontrada lanza excepción")
    @Tag("Valoraciones")
    void testCrearValoracionSolicitudNoEncontrada() {
        ValoracionCrearDTO dto = ValoracionCrearDTO.builder()
                .puntuacion(5)
                .idSolicitud(99)
                .idAutor(1)
                .idDestinatario(2)
                .build();

        when(solicitudesRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> valoracionesService.crearValoracion(dto));
        assertEquals("La solicitud no existe", exception.getMessage());

        verify(valoracionesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Crear valoración con autor no encontrado lanza excepción")
    @Tag("Valoraciones")
    void testCrearValoracionAutorNoEncontrado() {
        ValoracionCrearDTO dto = ValoracionCrearDTO.builder()
                .puntuacion(4)
                .idSolicitud(1)
                .idAutor(99)
                .idDestinatario(2)
                .build();

        Solicitudes solicitudMock = Solicitudes.builder().id(1).build();

        when(solicitudesRepository.findById(1)).thenReturn(Optional.of(solicitudMock));
        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> valoracionesService.crearValoracion(dto));
        assertEquals("El perfil autor no existe", exception.getMessage());

        verify(valoracionesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 4: Crear valoración válida la guarda correctamente")
    @Tag("Valoraciones")
    void testCrearValoracionExitosa() throws Exception {
        ValoracionCrearDTO dto = ValoracionCrearDTO.builder()
                .puntuacion(5)
                .comentario("¡Todo perfecto!")
                .idSolicitud(1)
                .idAutor(1)
                .idDestinatario(2)
                .build();

        Solicitudes solicitudMock = Solicitudes.builder().id(1).build();
        Perfil autorMock = Perfil.builder().id(1).nombre("Manuel").build();
        Perfil destinatarioMock = Perfil.builder().id(2).nombre("Ana").build();

        Valoraciones valoracionGuardada = Valoraciones.builder()
                .id(10)
                .puntuacion(5)
                .comentario("¡Todo perfecto!")
                .solicitud(solicitudMock)
                .autor(autorMock)
                .destinatario(destinatarioMock)
                .build();

        when(solicitudesRepository.findById(1)).thenReturn(Optional.of(solicitudMock));
        when(perfilRepository.findById(1)).thenReturn(Optional.of(autorMock));
        when(perfilRepository.findById(2)).thenReturn(Optional.of(destinatarioMock));
        when(valoracionesRepository.save(any(Valoraciones.class))).thenReturn(valoracionGuardada);

        Valoraciones resultado = valoracionesService.crearValoracion(dto);

        assertNotNull(resultado);
        assertEquals(5, resultado.getPuntuacion());
        assertEquals("¡Todo perfecto!", resultado.getComentario());

        verify(valoracionesRepository).save(any(Valoraciones.class));
    }
}
