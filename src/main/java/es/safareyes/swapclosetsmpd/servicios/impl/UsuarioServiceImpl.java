package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioUsuario;
import es.safareyes.swapclosetsmpd.dtos.UsuarioRegistroDTO;
import es.safareyes.swapclosetsmpd.modelos.Usuario;
import es.safareyes.swapclosetsmpd.servicios.IUsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioServiceImpl implements IUsuarioService{

    private final IRepositorioUsuario usuarioRepository;

    public UsuarioServiceImpl(IRepositorioUsuario usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(UsuarioRegistroDTO dto) throws Exception {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new Exception("El mail introducido no es válido");
        }

        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("El email ya está registrado");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .contrasena(dto.getPassword())
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }
}
