package it.unimib.wegather.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class UserContextConfiguration {

    @Bean
    @RequestScope
    public UserContext userContext() {
        return new UserContext();
    }
}
