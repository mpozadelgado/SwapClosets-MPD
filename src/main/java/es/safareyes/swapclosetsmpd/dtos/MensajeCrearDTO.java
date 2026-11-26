package es.safareyes.swapclosetsmpd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeCrearDTO {

    @NotBlank(message = "El contenido del mensaje no puede estar vacío")
    @Size(max = 1000, message = "El mensaje no puede tener más de 1000 caracteres")
    private String contenido;

    @NotNull(message = "El emisor es obligatorio")
    private Integer idEmisor;

    private Integer idReceptor;
}