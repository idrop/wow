package wow.web.dto;

import wow.core.api.CalculationSession;
import wow.core.api.Owing;
import wow.core.api.User;
import wow.web.validation.CurrencyConstraint;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class SessionDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @CurrencyConstraint
    private String currency;

    private List<UserDTO> userDTOs = new ArrayList<>();

    private List<Owing> owings = new ArrayList<>();

    public SessionDTO() {
    }

    public SessionDTO(CalculationSession calculationSession, List<Owing> owings) {
        this.owings = owings;

        this.setName(calculationSession.getName());
        this.setCurrency(calculationSession.getCurrencyUnit().getCurrencyCode());
        this.id = calculationSession.getId();

        List<User> users = calculationSession.getUsers();

        users.stream().forEach(u -> this.userDTOs.add(new UserDTO(u, id)));


    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUserDTOs(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
    }

    public void setOwings(List<Owing> owings) {
        this.owings = owings;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public List<UserDTO> getUserDTOs() {
        return userDTOs;
    }

    public List<Owing> getOwings() {
        return owings;
    }
}
