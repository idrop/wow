package wow.web.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {

    String message() default "Invalid currency";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
