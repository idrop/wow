package wow.core.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.money.UnknownCurrencyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculationSessionTest {


    @Test
    @DisplayName("cannot create session when name null")
    public void testSession1() {
        assertThrows(NullPointerException.class, () -> new CalculationSession(null, "GBP"));
    }

    @Test
    @DisplayName("cannot create session when currency invalid")
    public void testSession2() {
        assertThrows(UnknownCurrencyException.class, () -> new CalculationSession("name", "GGG"));
    }

    @Test
    @DisplayName("cannot create session when name blank")
    public void testSession3() {
        assertThrows(IllegalArgumentException.class, () -> new CalculationSession("", "GBP"));
    }

    @Test
    @DisplayName("when session created, then ii can retrieve id and details")
    public void testSession4() {
        CalculationSession session = getSession();
        assertThat(session.getId()).isNotBlank();
        assertThat(session.getName()).isEqualTo("name");
        assertThat(session.getCurrencyUnit().getCurrencyCode()).isEqualTo("GBP");
    }


    @Test
    @DisplayName("when user already added, throw IllegalStateException")
    public void addUser() {
        CalculationSession session = getSession();
        session.addUser("phil");
        assertThrows(IllegalStateException.class, () -> session.addUser("phil"));
    }

    private CalculationSession getSession() {
        return new CalculationSession("name", "GBP");
    }

}