package es.safareyes.swapclosetsmpd.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioRegistroDTO {
    private String username;
    private String email;
    private String password;

}
