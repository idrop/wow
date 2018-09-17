package wow.core.impl;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import wow.core.api.CalculationSession;
import wow.core.api.Owing;
import wow.core.api.User;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class CalculatorImplTest {

    @InjectMocks
    private CalculatorImpl calculator;


    private Supplier<CalculationSession> supplier;

    @Test
    public void test1() {


        CurrencyUnit gbp = Monetary.getCurrency("GBP");
        CalculationSession session = supplier.get();

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
        assertEquals(Money.of(300, gbp), owing.getAmount());
    }

    @Test
    public void test2() {


        CurrencyUnit gbp = Monetary.getCurrency("GBP");
        CalculationSession session = supplier.get();

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
    public void test3() {


        CurrencyUnit gbp = Monetary.getCurrency("GBP");
        CalculationSession session = supplier.get();

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
        assertEquals(Money.of(300, gbp), owing1.getAmount());

        Owing owing2 = owings.get(1);
        assertEquals(phil, owing2.getOwed());
        assertEquals(julie, owing2.getOwer());
        assertEquals(Money.of(1, gbp), owing2.getAmount());
    }

    @Test
    public void test4() {


        CurrencyUnit gbp = Monetary.getCurrency("GBP");
        CalculationSession session = supplier.get();

        User phil = session.addUser("phil");
        User julie = session.addUser("julie");
        User frieda = session.addUser("frieda");


        session.addPayment("a", phil, 300);
        session.addPayment("b", julie, 300);

        List<Owing> owings = calculator.calculate(session);
        assertTrue(owings.size() == 2);
        assertTrue(owings.size() == 2);
        Owing owing1 = owings.get(0);
        assertEquals(phil, owing1.getOwed());
        assertEquals(frieda, owing1.getOwer());
        assertEquals(Money.of(100, gbp), owing1.getAmount());

        Owing owing2 = owings.get(1);
        assertEquals(julie, owing2.getOwed());
        assertEquals(frieda, owing2.getOwer());
        assertEquals(Money.of(100, gbp), owing2.getAmount());
    }


    @Before
    public void before() {
        supplier = () -> new CalculationSession("test", "GBP");
    }
}