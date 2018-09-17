package wow.core.api;

import java.util.List;

public interface Calculator {

    List<Owing> calculate(CalculationSession command);
}