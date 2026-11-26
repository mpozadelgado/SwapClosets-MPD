package es.safareyes.swapclosetsmpd.Controladores;

import es.safareyes.swapclosetsmpd.excepciones.ElementoDuplicado;
import es.safareyes.swapclosetsmpd.excepciones.ElementoNoEncontrado;
import es.safareyes.swapclosetsmpd.excepciones.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorGlobalController {

    @ExceptionHandler(ElementoNoEncontrado.class)
    public ProblemDetail elementoNoEncontrado(ElementoNoEncontrado e) {
        return construir(HttpStatus.NOT_FOUND, "Elemento no encontrado", e.getMessage());
    }

    @ExceptionHandler(ElementoDuplicado.class)
    public ProblemDetail elementoDuplicado(ElementoDuplicado e) {
        return construir(HttpStatus.CONFLICT, "Elemento duplicado", e.getMessage());
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ProblemDetail reglaNegocio(ReglaNegocioException e) {
        return construir(HttpStatus.UNPROCESSABLE_ENTITY, "Regla de negocio no cumplida", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail erroresDTO(MethodArgumentNotValidException e) {
        Map<String, String> errores = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        ProblemDetail problema = construir(HttpStatus.BAD_REQUEST, "Datos no válidos", "Revisa los campos indicados");
        problema.setProperty("errores", errores);
        return problema;
    }

    private ProblemDetail construir(HttpStatus estado, String titulo, String detalle) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(estado, detalle);
        problema.setTitle(titulo);
        problema.setProperty("timestamp", LocalDateTime.now());
        return problema;
    }
}