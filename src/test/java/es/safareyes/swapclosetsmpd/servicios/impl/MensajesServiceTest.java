package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioMensajes;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.dtos.MensajeCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Mensajes;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.servicios.impl.MensajesServiceImpl;
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
class MensajesServiceTest {

    @InjectMocks
    private MensajesServiceImpl mensajesService;

    @Mock
    private IRepositorioMensajes mensajesRepository;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Test
    @DisplayName("Test 1: Enviar mensaje con contenido vacío lanza excepción")
    @Tag("Mensajes")
    void testEnviarMensajeTextoVacio() {
        MensajeCrearDTO dto = MensajeCrearDTO.builder()
                .contenido("")
                .idEmisor(1)
                .idReceptor(2)
                .build();

        Exception exception = assertThrows(Exception.class, () -> mensajesService.enviarMensaje(dto));
        assertEquals("El contenido del mensaje no puede estar vacío", exception.getMessage());

        verifyNoInteractions(mensajesRepository);
    }

    @Test
    @DisplayName("Test 2: Enviar mensaje con emisor inexistente lanza excepción")
    @Tag("Mensajes")
    void testEnviarMensajeEmisorNoEncontrado() {
        MensajeCrearDTO dto = MensajeCrearDTO.builder()
                .contenido("Hola, ¿sigue disponible?")
                .idEmisor(99)
                .idReceptor(2)
                .build();

        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> mensajesService.enviarMensaje(dto));
        assertEquals("El perfil emisor no existe", exception.getMessage());

        verify(mensajesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Enviar mensaje con receptor inexistente lanza excepción")
    @Tag("Mensajes")
    void testEnviarMensajeReceptorNoEncontrado() {
        MensajeCrearDTO dto = MensajeCrearDTO.builder()
                .contenido("Hola, ¿sigue disponible?")
                .idEmisor(1)
                .idReceptor(99)
                .build();

        Perfil emisorMock = Perfil.builder().id(1).nombre("Manuel").build();

        when(perfilRepository.findById(1)).thenReturn(Optional.of(emisorMock));
        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> mensajesService.enviarMensaje(dto));
        assertEquals("El perfil receptor no existe", exception.getMessage());

        verify(mensajesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 4: Enviar mensaje válido lo guarda correctamente")
    @Tag("Mensajes")
    void testEnviarMensajeExitoso() throws Exception {
        MensajeCrearDTO dto = MensajeCrearDTO.builder()
                .contenido("Hola, ¿sigue disponible?")
                .idEmisor(1)
                .idReceptor(2)
                .build();

        Perfil emisorMock = Perfil.builder().id(1).nombre("Manuel").build();
        Perfil receptorMock = Perfil.builder().id(2).nombre("Ana").build();

        Mensajes mensajeGuardado = Mensajes.builder()
                .id(100)
                .contenido("Hola, ¿sigue disponible?")
                .emisor(emisorMock)
                .receptor(receptorMock)
                .build();

        when(perfilRepository.findById(1)).thenReturn(Optional.of(emisorMock));
        when(perfilRepository.findById(2)).thenReturn(Optional.of(receptorMock));
        when(mensajesRepository.save(any(Mensajes.class))).thenReturn(mensajeGuardado);

        Mensajes resultado = mensajesService.enviarMensaje(dto);

        assertNotNull(resultado);
        assertEquals("Hola, ¿sigue disponible?", resultado.getContenido());

        verify(mensajesRepository).save(any(Mensajes.class));
    }
}
