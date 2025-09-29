package logs.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação de DTOs enviados no corpo da requisição (@Valid @RequestBody)
    // Exemplo: campos obrigatórios não preenchidos ou padrões incorretos
    // Retorna status 400 (Bad Request) com mapa campo -> mensagem de erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            erros.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(erros);
    }

    // Captura erros de validação de parâmetros em path variables ou request params
    // Exemplo: @Min, @Max, @NotNull aplicados em parâmetros do método
    // Retorna status 400 (Bad Request) com mapa campo -> mensagem de erro
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getConstraintViolations().forEach(cv -> {
            String campo = cv.getPropertyPath().toString();
            String mensagem = cv.getMessage();
            erros.put(campo, mensagem);
        });

        return ResponseEntity.badRequest().body(erros);
    }

    // Captura erros de parsing do JSON, por exemplo quando o formato de uma data
    // não bate com o esperado (dd-MM-yyyy)
    // Retorna status 400 (Bad Request) com campo e mensagem explicativa
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("campo", "nascimentoPessoa");
        erro.put("mensagem", "A data de nascimento deve estar no formato dd-MM-yyyy.");

        return ResponseEntity.badRequest().body(erro);
    }

    // Captura exceções quando uma entidade não é encontrada no banco
    // Exemplo: buscar ou deletar uma pessoa que não existe
    // Retorna status 404 (Not Found) com mensagem explicativa
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Captura erros de violação de restrição de unicidade no banco
    // Exemplo: CPF duplicado
    // Retorna status 409 (Conflict) com campo específico ou mensagem genérica
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> erro = new HashMap<>();

        String mensagem = ex.getMostSpecificCause().getMessage();

        if (mensagem != null && mensagem.contains("pes_cpf")) {
            erro.put("cpfPessoa", "Já existe uma pessoa cadastrada com este CPF.");
        } else {
            erro.put("erro", "Violação de integridade no banco de dados.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // Captura qualquer exceção genérica não tratada
    // Retorna status 500 (Internal Server Error) com mensagem de erro geral e detalhe
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Ocorreu um erro inesperado");
        erro.put("detalhe", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
