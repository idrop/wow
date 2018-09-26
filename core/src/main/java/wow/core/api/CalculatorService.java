package wow.core.api;

import java.util.List;

public interface CalculatorService {
    
    List<Owing> calculate(CalculationSession session);

    CalculationSession createSession(String name, String currency);

    CalculationSession getCalculationSession(String sessionId);

    User addUser(String sessionId, String name);
}
