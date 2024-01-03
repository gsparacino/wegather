package it.unimib.wegather;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ApplicationContext {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}
