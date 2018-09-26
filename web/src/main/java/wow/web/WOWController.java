package wow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wow.core.api.CalculationSession;
import wow.core.api.CalculatorService;
import wow.core.api.Owing;
import wow.core.api.User;
import wow.web.dto.SessionDTO;
import wow.web.dto.UserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller
public class WOWController {

    public static final String API_USER = "/user";
    public static final String API_SESSION = "/session";

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping(API_USER)
    @ResponseBody
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        @NotBlank String sessionId = userDTO.getSessionId();
        User user = calculatorService.addUser(sessionId, userDTO.getName());
        return new UserDTO(user, sessionId);
    }

    @PostMapping(API_SESSION)
    @ResponseBody
    public SessionDTO addSession(@Valid @RequestBody SessionDTO sessionDTO) {
        CalculationSession calculationSession = calculatorService.createSession(sessionDTO.getName(), sessionDTO.getCurrency());
        return createSessionDTO(calculationSession);
    }

    @GetMapping("/session/{sessionId}")
    @ResponseBody
    public SessionDTO getSession(@PathVariable String sessionId) {
        CalculationSession calculationSession = calculatorService.getCalculationSession(sessionId);
        SessionDTO sessionDTO = createSessionDTO(calculationSession);
        return sessionDTO;
    }

    private SessionDTO createSessionDTO(CalculationSession calculationSession) {
        List<Owing> owings = calculatorService.calculate(calculationSession);
        return new SessionDTO(calculationSession, owings);
    }

}
