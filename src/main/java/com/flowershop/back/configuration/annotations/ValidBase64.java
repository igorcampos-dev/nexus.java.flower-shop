package com.flowershop.back.configuration.annotations;

import com.flowershop.back.configuration.annotations.impl.ValidBase64Impl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Notação para validar se uma string codificada em Base64 representa uma imagem válida.
 * Pode ser aplicada a campos de classe ou métodos.
 *
 * Utilização:
 * Para usar esta anotação, aplique-a ao campo de string ou método que contém a representação
 * codificada em Base64 de uma imagem. A validação será realizada utilizando a implementação em
 * {@link ValidBase64Impl}.
 *
 * Exemplo:
 * {@code @ValidBase64(message = "A representação em Base64 não é uma imagem válida")}
 * private String imagemBase64;
 *
 * @author Igor de Campos Kopschinski
 *
 * @version 1.0
 *
 * Mensagem Padrão:
 * A mensagem padrão exibida quando a validação falha é "Imagem inválida".
 * Esta mensagem pode ser personalizada ao aplicar a anotação.
 *
 * @see ValidBase64Impl
 */
@Documented
@Constraint(validatedBy = ValidBase64Impl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBase64 {
    String message() default "Imagem inválida";
}
