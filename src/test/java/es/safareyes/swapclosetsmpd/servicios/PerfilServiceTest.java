package es.safareyes.swapclosetsmpd.servicios;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioUsuario;
import es.safareyes.swapclosetsmpd.dtos.PerfilCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Usuario;
import es.safareyes.swapclosetsmpd.servicios.impl.PerfilServiceImpl;
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
public class PerfilServiceTest {

    @InjectMocks
    private PerfilServiceImpl perfilService;

    @Mock
    private IRepositorioPerfil perfilRepository;

    @Mock
    private IRepositorioUsuario usuarioRepository;

    @Test
    @DisplayName("Test 1: Crear perfil con email inválido lanza excepción")
    @Tag("Perfil")
    void testCrearPerfilEmailInvalido() {
        PerfilCrearDTO dto = PerfilCrearDTO.builder()
                .nombre("Manuel")
                .email("email_invalido")
                .usuarioId(1)
                .build();

        Exception exception = assertThrows(Exception.class, () -> perfilService.crearPerfil(dto));
        assertEquals("El email del perfil no es válido", exception.getMessage());

        verifyNoInteractions(perfilRepository);
    }

    @Test
    @DisplayName("Test 2: Crear perfil con id_usuario no encontrado lanza excepción")
    @Tag("Perfil")
    void testCrearPerfilUsuarioNoEncontrado() {
        PerfilCrearDTO dto = PerfilCrearDTO.builder()
                .nombre("Manuel")
                .email("manuel@ejemplo.com")
                .usuarioId(999)
                .build();

        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> perfilService.crearPerfil(dto));
        assertEquals("El usuario no existe", exception.getMessage());

        verify(usuarioRepository).findById(999);
        verify(perfilRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Crear perfil con datos válidos lo guarda correctamente")
    @Tag("Perfil")
    void testCrearPerfilExitoso() throws Exception {
        PerfilCrearDTO dto = PerfilCrearDTO.builder()
                .nombre("Manuel")
                .apellido1("García")
                .email("manuel@ejemplo.com")
                .talla("M")
                .usuarioId(1)
                .build();

        Usuario usuarioMock = Usuario.builder().id(1).username("manuel").build();
        Perfil perfilGuardado = Perfil.builder()
                .id(10)
                .nombre("Manuel")
                .apellido1("García")
                .email("manuel@ejemplo.com")
                .talla("M")
                .usuario(usuarioMock)
                .build();

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioMock));
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfilGuardado);

        Perfil resultado = perfilService.crearPerfil(dto);

        assertNotNull(resultado);
        assertEquals("Manuel", resultado.getNombre());
        assertEquals("García", resultado.getApellido1());
        assertEquals("manuel@ejemplo.com", resultado.getEmail());

        verify(usuarioRepository).findById(1);
        verify(perfilRepository).save(any(Perfil.class));
    }
}
