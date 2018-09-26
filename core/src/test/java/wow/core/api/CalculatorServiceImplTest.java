package wow.core.api;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class CalculatorServiceImplTest {

    private CalculatorService calculator;

    @Test
    @DisplayName("can create a session")
    public void testSession1() {
        CalculationSession session = calculator.createSession("name", "GBP");
        assertThat(session.getName()).isEqualTo("name");
        assertThat(session.getCurrencyUnit().getCurrencyCode()).isEqualTo("GBP");
    }

    @Test
    @DisplayName("cannot create session when currency invalid")
    public void testSession2() {
        assertThrows(UnknownCurrencyException.class, () -> calculator.createSession("name", "GGG"));
    }

    @Test
    @DisplayName("cannot create session when name blank")
    public void testSession3() {
        assertThrows(IllegalArgumentException.class, () -> calculator.createSession("", "GBP"));
    }


    @Test
    @DisplayName("session should calculate owings")
    public void test1() {


        CalculationSession session = calculator.createSession("test", "GBP");


        User phil = session.addUser("phil");
        User julie = session.addUser("julie");
        User frieda = session.addUser("frieda");

        session.addPayment("a", phil, 600);
        session.addPayment("b", julie, 300);

        List<Owing> owings = calculator.calculate(session);
        assertTrue(owings.size() == 1);
        Owing owing = owings.iterator().next();
        assertEquals(phil, owing.getOwed());
        assertEquals(frieda, owing.getOwer());
        assertEquals(Money.of(300, session.getCurrencyUnit()), owing.getAmount());
    }

    @Test
    @DisplayName("calculate ower owing 2 others")
    public void test2() {


        CurrencyUnit gbp = Monetary.getCurrency("GBP");
        CalculationSession session = calculator.createSession("test", "GBP");

        User phil = session.addUser("phil");
        User julie = session.addUser("julie");
        User frieda = session.addUser("frieda");


        session.addPayment("a", phil, 599);
        session.addPayment("a", julie, 301);

        List<Owing> owings = calculator.calculate(session);
        assertTrue(owings.size() == 2);
        Owing owing1 = owings.get(0);
        assertEquals(phil, owing1.getOwed());
        assertEquals(frieda, owing1.getOwer());
        assertEquals(Money.of(299, gbp), owing1.getAmount());

        Owing owing2 = owings.get(1);
        assertEquals(julie, owing2.getOwed());
        assertEquals(frieda, owing2.getOwer());
        assertEquals(Money.of(1, gbp), owing2.getAmount());
    }

    @Test
    @DisplayName("calculate 2 owers to one owed")
    public void test3() {


        CalculationSession session = calculator.createSession("test", "GBP");

        User phil = session.addUser("phil");
        User julie = session.addUser("julie");
        User frieda = session.addUser("frieda");


        session.addPayment("a", phil, 601);
        session.addPayment("a", julie, 299);

        List<Owing> owings = calculator.calculate(session);
        assertTrue(owings.size() == 2);
        Owing owing1 = owings.get(0);
        assertEquals(phil, owing1.getOwed());
        assertEquals(frieda, owing1.getOwer());
        assertEquals(Money.of(300, session.getCurrencyUnit()), owing1.getAmount());

        Owing owing2 = owings.get(1);
        assertEquals(phil, owing2.getOwed());
        assertEquals(julie, owing2.getOwer());
        assertEquals(Money.of(1, session.getCurrencyUnit()), owing2.getAmount());
    }

    @Test
    @DisplayName("all owings resolved")
    public void test4() {

        CalculationSession session = calculator.createSession("test", "GBP");

        User phil = session.addUser("phil");
        User julie = session.addUser("julie");
        User frieda = session.addUser("frieda");


        session.addPayment("a", phil, 300);
        session.addPayment("b", julie, 300);
        session.addPayment("c", frieda, 200);
        session.addPayment("d", frieda, 100);

        List<Owing> owings = calculator.calculate(session);
        assertTrue(owings.isEmpty());
    }


    @BeforeEach
    public void before() {
        calculator = new CalculatorServiceImpl();
    }
}