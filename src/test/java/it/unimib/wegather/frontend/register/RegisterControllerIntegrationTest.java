package it.unimib.wegather.frontend.register;


import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.login.LoginController;
import it.unimib.wegather.frontend.login.model.LoginModel;
import it.unimib.wegather.frontend.register.model.RegisterModel;
import it.unimib.wegather.service.AuthenticationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
@Import(UserContextConfiguration.class)
class RegisterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;


    @Test
    @SneakyThrows
    void testRegistrazione() {
        // Context
        String username = "user";
        RegisterModel model = new RegisterModel();
        model.setUsername(username);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/" + viewId(Views.REGISTER ))
                        .flashAttr("register", model)
                        .param("username", "user")
        );

        // Assertions
        result.andExpect(view().name(viewId(Views.REGISTER)));


    }

    @Test
    @SneakyThrows
    void testUtenteNonRegistrato() {
        // Context
        String username = "user";
        String email = "email@example.com";
        RegisterModel model = new RegisterModel();
        model.setUsername(username);
        model.setEmail(email);
        when(authenticationService.register(username,email)).thenReturn(true);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/" + viewId(Views.REGISTER ))
                        .flashAttr("register", model)
        );

        // Assertions
        String expectedViewName = UriComponentsBuilder.fromPath(redirect(Views.REGISTER))
                .queryParam("username", username)
                .build()
                .encode()
                .toString();
        result
                .andExpect(view().name(expectedViewName));

    }


    @Test
    @SneakyThrows
    void testUtenteRegistrato() {
        // Context
        String username = "user";
        String email = "email@example.com";
        RegisterModel model = new RegisterModel();
        model.setUsername(username);
        model.setEmail(email);
        when(authenticationService.register(email,username)).thenReturn(true); // modifica qui

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/" + viewId(Views.REGISTER ))
                        .flashAttr("register", model)
        );

        // Assertions
        result.andExpect(view().name(redirect(Views.LISTA))); // modifica qui
    }


}
