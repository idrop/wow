package wow.web.dto;

import wow.web.validation.CurrencyConstraint;

import javax.validation.constraints.NotBlank;

public class SessionDTO {

    @NotBlank
    private String name;

    @NotBlank
    @CurrencyConstraint
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
