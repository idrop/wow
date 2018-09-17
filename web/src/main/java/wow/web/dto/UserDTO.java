package wow.web.dto;

import javax.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank
    private String sessionId;

    @NotBlank
    private String name;

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
}
