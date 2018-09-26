package wow.web.dto;

import javax.money.MonetaryAmount;

public class PaymentDTO {
    private final String name;
    private final MonetaryAmount amount;

    public PaymentDTO(String name, MonetaryAmount amount) {

        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }
}
