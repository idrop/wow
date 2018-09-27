package wow.core.api;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {


    private String name;
    private List<Payment> payments = new ArrayList<>();


    User(String name) {
        Objects.requireNonNull(name, "name is null");
        if (name.isEmpty()) {
            throw new IllegalStateException("user name is blank");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    void addPayment(String name, MonetaryAmount amount) {
        Payment payment = new Payment(name, amount);
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }

    MonetaryAmount getTotalSpend(CurrencyUnit currencyUnit) {
        MonetaryAmount total = Money.of(0, currencyUnit);
        for (Payment payment : payments) {
            total = total.add(payment.getAmount());
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
