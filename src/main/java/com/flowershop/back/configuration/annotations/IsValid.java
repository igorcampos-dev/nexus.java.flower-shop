package com.flowershop.back.configuration.annotations;

import com.flowershop.back.configuration.annotations.impl.IsValidImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Notação para validar se um campo de string é nulo ou vazio.
 * Esta anotação pode ser aplicada a campos de classe ou parâmetros de método.
 *
 * Utilização:
 * Para usar esta anotação, aplique-a ao campo desejado na classe ou ao parâmetro do método.
 * A validação será realizada utilizando a implementação em {@link IsValidImpl}.
 *
 * Exemplo:
 * {@code @IsValid(message = "O campo não pode ser nulo ou vazio")}
 * private String meuCampo;
 *
 * @author Igor de Campos Kopschinski
 *
 * @version 1.0
 *
 * Mensagem Padrão:
 * A mensagem padrão exibida quando a validação falha é "A string não é válida".
 * Esta mensagem pode ser personalizada ao aplicar a anotação.
 *
 * @see IsValidImpl
 */

@Documented
@Constraint(validatedBy = IsValidImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValid {
    String message() default "A string não é válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
