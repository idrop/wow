package wow.core.api;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;

public class CalculatorServiceImpl implements CalculatorService {


    private Map<String, CalculationSession> sessionMap = new HashMap<>();

    @Override
    public List<Owing> calculate(CalculationSession session) {
        CurrencyUnit currencyUnit = session.getCurrencyUnit();
        List<User> users = session.getUsers();
        List<Owing> owings = new ArrayList<>();
        if (users.isEmpty()) {
            return owings;
        }
        MonetaryAmount total = Money.of(0, currencyUnit);
        for (User user : users) {
            MonetaryAmount totalSpend = user.getTotalSpend(currencyUnit);
            total = total.add(totalSpend);
        }

        MonetaryAmount divided = total.divide(users.size());


        // sort users
        LinkedList<User> sorted = users.stream().sorted((user, t1) -> t1.getTotalSpend(currencyUnit).compareTo(user.getTotalSpend(currencyUnit))).collect(toCollection(LinkedList::new));


        User owed = getNextOwed(sorted);
        User ower = getNextOwing(sorted);

        while (owed != null) {

            while (owed.getTotalSpend(currencyUnit).subtract(getAmountAlreadyPaid(owings, owed, currencyUnit)).subtract(divided).isPositive()) {

                while (ower != null && divided.subtract(ower.getTotalSpend(currencyUnit).add(getAmountAlreadyPaid(owings, ower, currencyUnit))).isPositive()) {

                    // owe 150, owed = 100
                    if (divided.subtract(ower.getTotalSpend(currencyUnit).add(getAmountAlreadyPaid(owings, ower, currencyUnit))).isGreaterThan(owed.getTotalSpend(currencyUnit).subtract(getAmountAlreadyPaid(owings, owed, currencyUnit)).subtract(divided))) {
                        owings.add(new Owing(ower, owed.getTotalSpend(currencyUnit).subtract(getAmountAlreadyPaid(owings, owed, currencyUnit)).subtract(divided), owed));
                        owed = getNextOwed(sorted);
                    } else { // owe 100, owed = 150
                        owings.add(new Owing(ower, divided.subtract(ower.getTotalSpend(currencyUnit).add(getAmountAlreadyPaid(owings, ower, currencyUnit))), owed));
                        ower = getNextOwing(sorted);
                    }

                }

                ower = getNextOwing(sorted);

            }


            owed = getNextOwed(sorted);

        }


        return owings;

    }

    private User getNextOwed(LinkedList<User> sorted) {
        if (sorted.peekFirst() != null) {
            return sorted.removeFirst();
        } else {
            return null;
        }
    }

    private User getNextOwing(LinkedList<User> sorted) {
        if (sorted.peekLast() != null) {
            return sorted.removeLast();
        } else {
            return null;
        }
    }

    private MonetaryAmount getAmountAlreadyPaid(List<Owing> owings, User user, CurrencyUnit currencyUnit) {

        return owings
                .stream()
                .filter(owing -> user.equals(owing.getOwed()) || user.equals(owing.getOwer()))
                .map(Owing::getAmount)
                .reduce(Money.of(0, currencyUnit), (a, b) -> a.add(b));
    }

    @Override
    public CalculationSession createSession(String name, String currency) {
        CalculationSession calculationSession = new CalculationSession(name, currency);
        sessionMap.put(calculationSession.getId(), calculationSession);
        return calculationSession;
    }

    @Override
    public CalculationSession getCalculationSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public User addUser(String sessionId, String name) {

        CalculationSession calculationSession = getCalculationSession(sessionId);
        if (calculationSession != null) {
            return calculationSession.addUser(name);

        } else {
            throw new IllegalStateException(format("session id %s not found", sessionId));
        }

    }
}
