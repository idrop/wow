package wow.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import wow.core.api.CalculationSession;
import wow.core.api.User;
import wow.web.dto.SessionDTO;
import wow.web.dto.UserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Controller
public class WOWController {


    public static final String API_USER = "/user";
    public static final String API_SESSION = "/session";
    private Map<String, CalculationSession> sessionMap = new HashMap<>();


    @PostMapping(API_USER)
    @ResponseBody

    public User userAdd(@Valid @RequestBody UserDTO userDTO) {

        @NotBlank String sessionId = userDTO.getSessionId();
        CalculationSession calculationSession = sessionMap.get(sessionId);
        if (calculationSession != null) {
            return calculationSession.addUser(userDTO.getName());
        } else {
            throw new IllegalStateException(format("session id %s not found", sessionId));
        }

    }

    @PostMapping(API_SESSION)
    @ResponseBody
    public String sessionAdd(@Valid @RequestBody SessionDTO sessionDTO) {

        CalculationSession calculationSession = new CalculationSession(sessionDTO.getName(), sessionDTO.getCurrency());
        String id = calculationSession.getId();
        sessionMap.put(id, calculationSession);
        return id;
    }

}
