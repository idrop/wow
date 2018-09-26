package wow.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wow.core.api.CalculatorService;
import wow.core.api.CalculatorServiceImpl;

@Configuration
public class WhoOwesWhoConfiguration {

    @Bean
    public CalculatorService calculatorService() {
        return new CalculatorServiceImpl();
    }

}
