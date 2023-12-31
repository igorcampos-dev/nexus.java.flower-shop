package com.flowershop.back.exceptions.handler;

import com.flowershop.back.exceptions.*;
import com.flowershop.back.exceptions.object.Error;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleValidationException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Dados inválido. Certifique-se de que os Dados fornecidos corresponde ao Dados esperados.")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(s.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(UserPendingActivationException.class)
    public ResponseEntity<Error> userPendingException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Sua conta está pendente de ativação. Por favor, verifique seu email e siga as instruções de ativação antes de fazer login.")
                .status(HttpStatus.FORBIDDEN.value())
                .path(s.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> userAlreadyExits(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.")
                .status(HttpStatus.CONFLICT.value())
                .path(s.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Error> messagingException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> invalidEmailException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Email inválido. Verifique o formato do email fornecido.")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(FlowerNotFoundException.class)
    public ResponseEntity<Error> flowerNotFoundException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Flor não encontrada")
                .status(HttpStatus.NOT_FOUND.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(FlowerAlreadyExistsException.class)
    public ResponseEntity<Error> flowerAlreadyExistsException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Já existe um flor com certas informações. Por favor, escolha informações diferentes.")
                .status(HttpStatus.CONFLICT.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> userNotFoundException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("Usuário não encontrado")
                .status(HttpStatus.NOT_FOUND.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("dado passado é inválido")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Error> invalidCredentialsException(HttpServletRequest s){
        Error error = Error.builder()
                .timestamp(instant)
                .message("credenciais incorretas")
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(s.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest s) {
        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(String.format(
                    "o campo '%s' %s",
                    violation.getPropertyPath(),
                    violation.getMessage()));
        }

        Error error = Error.builder()
                .timestamp(instant)
                .message(errorMessage.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(s.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
