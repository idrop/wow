package wow.core.api;

import javax.money.MonetaryAmount;
import java.util.Objects;

public class Payment {

    private MonetaryAmount amount;
    private String name;

    Payment(String name, MonetaryAmount amount) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(amount);
        this.name = name;
        this.amount = amount;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
