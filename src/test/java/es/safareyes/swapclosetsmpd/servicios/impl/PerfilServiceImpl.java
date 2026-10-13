package es.safareyes.swapclosetsmpd.servicios.impl;

import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioPerfil;
import es.safareyes.swapclosetsmpd.Repositorios.IRepositorioUsuario;
import es.safareyes.swapclosetsmpd.dtos.PerfilCrearDTO;
import es.safareyes.swapclosetsmpd.modelos.Perfil;
import es.safareyes.swapclosetsmpd.modelos.Usuario;
import es.safareyes.swapclosetsmpd.servicios.IPerfilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilServiceImpl implements IPerfilService{

    private final IRepositorioPerfil perfilRepository;
    private final IRepositorioUsuario usuarioRepository;

    public PerfilServiceImpl(IRepositorioPerfil perfilRepository, IRepositorioUsuario usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Perfil crearPerfil(PerfilCrearDTO dto) throws Exception {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new Exception("El email del perfil no es válido");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new Exception("El usuario no existe"));

        Perfil perfil = Perfil.builder()
                .nombre(dto.getNombre())
                .apellido1(dto.getApellido1())
                .apellido2(dto.getApellido2())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .biografia(dto.getBiografia())
                .talla(dto.getTalla())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
    }
}
