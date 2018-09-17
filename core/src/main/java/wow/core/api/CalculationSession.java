package wow.core.api;


import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.*;

import static java.lang.String.format;

public class CalculationSession {

    private final CurrencyUnit currencyUnit;

    private List<User> users;

    private String name;

    private String id;


    public CalculationSession(String name, String currencyUnit) {
        this.name = name;
        Objects.requireNonNull(currencyUnit);
        // todo validate curr
        this.currencyUnit = Monetary.getCurrency(currencyUnit);

        users = new ArrayList<>();
        id = UUID.randomUUID().toString();
    }

    public User addUser(String userName) {

        User userToAdd = new User(userName);

        if (users.contains(userToAdd)) {
            throw new IllegalStateException("user " + userName + " already added");
        }

        users.add(userToAdd);

        return userToAdd;

    }

    public synchronized void addPayment(String name, User user, Number number) {

        Objects.requireNonNull(name);
        Objects.requireNonNull(number);
        Objects.requireNonNull(user);

        MonetaryAmount amount = Money.of(number, currencyUnit);
        if (amount.isNegativeOrZero()) {
            throw new IllegalStateException(format("number %s is illegal for adding payment", amount));
        }

        Optional<User> first = users.stream().filter(u -> u.equals(user)).findFirst();
        if (first.isPresent()) {
            first.get().addPayment(name, amount);
        } else {
            throw new IllegalStateException(format("user %s not found", user));
        }


    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit; // immutable
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}