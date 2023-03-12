package minha.api.MinhaAPI.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Anotação do Spring para controolers de tratamento de erros.
public class TratadorDeErros {

    /*
        Em qualquer momento do nosso projeto que tiver um erro do tipo
        EntityNotFoundException, o Spring irá chamar esse método para
        tratar o caso.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity TratarErro404() {
        return ResponseEntity.notFound().build();
    }

}
