package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioUsuario;
import es.safareyes.swapclosetsmpd.dtos.UsuarioRegistroDTO;
import es.safareyes.swapclosetsmpd.modelos.Usuario;
import es.safareyes.swapclosetsmpd.servicios.impl.UsuarioServiceImpl;
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
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private IRepositorioUsuario usuarioRepository;

    @Test
    @DisplayName("Test 1: Registrar usuario con email inválido lanza excepción")
    @Tag("Usuario")
    void testRegistrarUsuarioEmailInvalido() {
        UsuarioRegistroDTO dto = UsuarioRegistroDTO.builder()
                .username("manuel")
                .email("email-sin-formato")
                .password("123456")
                .build();

        Exception exception = assertThrows(Exception.class, () -> usuarioService.registrarUsuario(dto));
        assertEquals("El mail introducido no es válido", exception.getMessage());

        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Test 2: Registrar usuario con email duplicado lanza excepción")
    @Tag("Usuario")
    void testRegistrarUsuarioEmailExistente() {
        UsuarioRegistroDTO dto = UsuarioRegistroDTO.builder()
                .username("manuel")
                .email("manuel@ejemplo.com")
                .password("123456")
                .build();

        when(usuarioRepository.existsByEmail("manuel@ejemplo.com")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> usuarioService.registrarUsuario(dto));
        assertEquals("El email ya está registrado", exception.getMessage());

        verify(usuarioRepository, times(1)).existsByEmail("manuel@ejemplo.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test 3: Registrar usuario con datos válidos se guarda correctamente")
    @Tag("Usuario")
    void testRegistrarUsuarioExitoso() throws Exception {
        UsuarioRegistroDTO dto = UsuarioRegistroDTO.builder()
                .username("manuel")
                .email("manuel@ejemplo.com")
                .password("123456")
                .build();

        Usuario usuarioGuardado = Usuario.builder()
                .id(1)
                .username("manuel")
                .email("manuel@ejemplo.com")
                .contrasena("123456")
                .build();

        when(usuarioRepository.existsByEmail("manuel@ejemplo.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        Usuario resultado = usuarioService.registrarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("manuel", resultado.getUsername());
        assertEquals("manuel@ejemplo.com", resultado.getEmail());

        verify(usuarioRepository).existsByEmail("manuel@ejemplo.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }
}
