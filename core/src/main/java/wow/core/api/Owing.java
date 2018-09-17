package wow.core.api;

import javax.money.MonetaryAmount;

public class Owing {

    private User ower;
    private MonetaryAmount amount;
    private User owed;


    public Owing(User ower, MonetaryAmount amount, User owed) {
        this.ower = ower;
        this.amount = amount;
        this.owed = owed;
    }

    public User getOwer() {
        return ower;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public User getOwed() {
        return owed;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Owing{");
        sb.append("ower=").append(ower);
        sb.append(", amount=").append(amount);
        sb.append(", owed=").append(owed);
        sb.append('}');
        return sb.toString();
    }
}
