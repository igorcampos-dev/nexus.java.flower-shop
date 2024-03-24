package com.nexus.back.exception.handler;

import com.nexus.back.exception.*;
import com.nexus.back.exception.object.Error;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Instant instant = Instant.now();

    private ResponseEntity<Error> response(String message, HttpStatus status, String uri){
        return ResponseEntity
                .status(status)
                .body(Error.builder()
                        .timestamp(instant)
                        .message(message)
                        .status(status.value())
                        .path(uri)
                        .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleValidationException(HttpServletRequest s){
        String message = "Dados inválido. Certifique-se de que os Dados fornecidos corresponde ao Dados esperados.";
        return this.response(message, HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(UserPendingActivationException.class)
    public ResponseEntity<Error> userPendingException(HttpServletRequest s){
        String message = "Sua conta está pendente de ativação. Por favor, verifique seu email e siga as instruções de ativação antes de fazer login.";
        return this.response(message, HttpStatus.FORBIDDEN, s.getRequestURI());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> userAlreadyExits(HttpServletRequest s){
        String message = "Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.";
        return this.response(message, HttpStatus.CONFLICT, s.getRequestURI());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Error> messagingException(HttpServletRequest s){
        String message = "O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.";
        return this.response(message, HttpStatus.INTERNAL_SERVER_ERROR, s.getRequestURI());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> invalidEmailException(HttpServletRequest s){
        String message = "Email inválido. Verifique o formato do email fornecido.";
        return this.response(message, HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(FlowerNotFoundException.class)
    public ResponseEntity<Error> flowerNotFoundException(HttpServletRequest s){
        String message = "Flor não encontrada";
        return this.response(message, HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(FlowerAlreadyExistsException.class)
    public ResponseEntity<Error> flowerAlreadyExistsException(HttpServletRequest s){
        String message = "Já existe um flor com certas informações. Por favor, escolha informações diferentes.";
        return this.response(message, HttpStatus.CONFLICT, s.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> userNotFoundException(HttpServletRequest s){
        String message = "Usuário não encontrado";
        return this.response(message, HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(HttpServletRequest s){
        String message = "dado passado é inválido";
        return this.response(message, HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Error> invalidCredentialsException(HttpServletRequest s){
        String message = "credenciais incorretas";
        return this.response(message, HttpStatus.UNAUTHORIZED, s.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest s) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getConstraintViolations().forEach( constraintViolation -> {
            errorMessage.append(String.format("o campo '%s' %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
        });

        return this.response(errorMessage.toString(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }
}
