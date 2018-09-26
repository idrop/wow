package wow.web.dto;

import wow.core.api.User;
import wow.core.api.Payment;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    @NotBlank
    private String sessionId;

    @NotBlank
    private String name;
    private List<PaymentDTO> payments = new ArrayList<>();

    public UserDTO() {
    }


    public UserDTO(User user, String sessionId) {
        setName(user.getName());


        setSessionId(sessionId);
        List<Payment> payments = user.getPayments();
        for (Payment payment : payments) {
            PaymentDTO paymentDTO = new PaymentDTO(payment.getName(), payment.getAmount());
            this.payments.add(paymentDTO);
        }


    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }
}
