package wow.web.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyValidatorTest {

    @Test
    @DisplayName("when invalid currency, then return false")
    public void invalid_currency() {
        assertFalse(new CurrencyValidator().isValid("GGG", null));
    }

    @Test
    @DisplayName("when valid currency, then return true")
    public void valid_currency() {
        assertTrue(new CurrencyValidator().isValid("GBP", null));
    }

}