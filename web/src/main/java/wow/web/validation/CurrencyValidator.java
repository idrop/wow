package wow.web.validation;

import javax.money.Monetary;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, String> {
    public void initialize(CurrencyConstraint constraint) {
    }

    public boolean isValid(String currency, ConstraintValidatorContext context) {

        if (Objects.nonNull(currency)) {

            try {
                Monetary.getCurrency(currency);
                return true;
            } catch (Exception e) {
            }


        }
        return false;


    }
}

