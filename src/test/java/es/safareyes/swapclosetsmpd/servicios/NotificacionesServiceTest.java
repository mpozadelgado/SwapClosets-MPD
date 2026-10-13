package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioNotificaciones;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.dtos.NotificacionCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Notificaciones;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.servicios.impl.NotificacionesServiceImpl;
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
class NotificacionesServiceTest {

    @InjectMocks
    private NotificacionesServiceImpl notificacionesService;

    @Mock
    private IRepositorioNotificaciones notificacionesRepository;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Test
    @DisplayName("Test 1: Crear notificación con mensaje vacío lanza excepción")
    @Tag("Notificaciones")
    void testCrearNotificacionMensajeVacio() {
        NotificacionCrearDTO dto = NotificacionCrearDTO.builder()
                .mensaje("")
                .idPerfil(1)
                .build();

        Exception exception = assertThrows(Exception.class, () -> notificacionesService.crearNotificacion(dto));
        assertEquals("El mensaje de la notificación no puede estar vacío", exception.getMessage());

        verifyNoInteractions(notificacionesRepository);
    }

    @Test
    @DisplayName("Test 2: Crear notificación con perfil inexistente lanza excepción")
    @Tag("Notificaciones")
    void testCrearNotificacionPerfilNoEncontrado() {
        NotificacionCrearDTO dto = NotificacionCrearDTO.builder()
                .mensaje("Tienes una nueva solicitud")
                .idPerfil(99)
                .build();

        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> notificacionesService.crearNotificacion(dto));
        assertEquals("El perfil no existe", exception.getMessage());

        verify(notificacionesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Crear notificación válida la guarda correctamente")
    @Tag("Notificaciones")
    void testCrearNotificacionExitosa() throws Exception {
        NotificacionCrearDTO dto = NotificacionCrearDTO.builder()
                .mensaje("Tienes una nueva solicitud")
                .tipo("SOLICITUD")
                .idPerfil(1)
                .build();

        Perfil perfilMock = Perfil.builder().id(1).nombre("Manuel").build();
        Notificaciones notificacionGuardada = Notificaciones.builder()
                .id(10)
                .mensaje("Tienes una nueva solicitud")
                .tipo("SOLICITUD")
                .leido(false)
                .perfil(perfilMock)
                .build();

        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfilMock));
        when(notificacionesRepository.save(any(Notificaciones.class))).thenReturn(notificacionGuardada);

        Notificaciones resultado = notificacionesService.crearNotificacion(dto);

        assertNotNull(resultado);
        assertEquals("Tienes una nueva solicitud", resultado.getMensaje());
        assertEquals("SOLICITUD", resultado.getTipo());
        assertFalse(resultado.getLeido());

        verify(notificacionesRepository).save(any(Notificaciones.class));
    }
}
