package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPrenda;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioSolicitudes;
import es.safareyes.swapclosetsmpd.dtos.SolicitudCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Prenda;
import es.safareyes.swapclosetsmpd.modelos.Solicitudes;
import es.safareyes.swapclosetsmpd.servicios.impl.SolicitudesServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolicitudesServiceTest {

    @InjectMocks
    private SolicitudesServiceImpl solicitudesService;

    @Mock
    private IRepositorioSolicitudes solicitudesRepository;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Mock
    private IRepositorioPrenda prendaRepository;

    @Test
    @DisplayName("Test 1: Crear solicitud sin tipo de solicitud lanza excepción")
    @Tag("Solicitudes")
    void testCrearSolicitudTipoVacio() {
        SolicitudCrearDTO dto = SolicitudCrearDTO.builder()
                .tiposolicitud("")
                .idPerfil(1)
                .idPrenda(10)
                .build();

        Exception exception = assertThrows(Exception.class, () -> solicitudesService.crearSolicitud(dto));
        assertEquals("El tipo de solicitud es obligatorio", exception.getMessage());

        verifyNoInteractions(solicitudesRepository);
    }

    @Test
    @DisplayName("Test 2: Crear solicitud con perfil inexistente lanza excepción")
    @Tag("Solicitudes")
    void testCrearSolicitudPerfilNoEncontrado() {
        SolicitudCrearDTO dto = SolicitudCrearDTO.builder()
                .tiposolicitud("INTERCAMBIO")
                .idPerfil(99)
                .idPrenda(10)
                .build();

        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> solicitudesService.crearSolicitud(dto));
        assertEquals("El perfil solicitante no existe", exception.getMessage());

        verify(solicitudesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Crear solicitud con prenda inexistente lanza excepción")
    @Tag("Solicitudes")
    void testCrearSolicitudPrendaNoEncontrada() {
        SolicitudCrearDTO dto = SolicitudCrearDTO.builder()
                .tiposolicitud("INTERCAMBIO")
                .idPerfil(1)
                .idPrenda(99)
                .build();

        Perfil perfilMock = Perfil.builder().id(1).nombre("Manuel").build();
        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfilMock));
        when(prendaRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> solicitudesService.crearSolicitud(dto));
        assertEquals("La prenda solicitada no existe", exception.getMessage());

        verify(solicitudesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 4: Crear solicitud con datos válidos la guarda correctamente")
    @Tag("Solicitudes")
    void testCrearSolicitudExitosa() throws Exception {
        SolicitudCrearDTO dto = SolicitudCrearDTO.builder()
                .tiposolicitud("INTERCAMBIO")
                .fechafin(LocalDate.now().plusDays(7))
                .idPerfil(1)
                .idPrenda(10)
                .build();

        Perfil perfilMock = Perfil.builder().id(1).nombre("Manuel").build();
        Prenda prendaMock = Prenda.builder().id(10).titulo("Chaqueta").build();
        Solicitudes solicitudGuardada = Solicitudes.builder()
                .id(100)
                .tiposolicitud("INTERCAMBIO")
                .estado("PENDIENTE")
                .perfil(perfilMock)
                .prenda(prendaMock)
                .build();

        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfilMock));
        when(prendaRepository.findById(10)).thenReturn(Optional.of(prendaMock));
        when(solicitudesRepository.save(any(Solicitudes.class))).thenReturn(solicitudGuardada);

        Solicitudes resultado = solicitudesService.crearSolicitud(dto);

        assertNotNull(resultado);
        assertEquals("INTERCAMBIO", resultado.getTiposolicitud());
        assertEquals("PENDIENTE", resultado.getEstado());

        verify(perfilRepository).findById(1);
        verify(prendaRepository).findById(10);
        verify(solicitudesRepository).save(any(Solicitudes.class));
    }
}
