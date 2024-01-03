package it.unimib.wegather.frontend.login;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.login.model.LoginModel;
import it.unimib.wegather.service.AuthenticationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(UserContextConfiguration.class)
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @SneakyThrows
    void testLogin() {
        // Context


        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/login")
        );

        // Assertions
        result
                .andExpect(status().isOk())
                .andExpect(view().name(viewId(Views.LOGIN)))
        ;
    }

    @Test
    @SneakyThrows
    void testUtenteNonAutenticato() {
        // Context
        when(authenticationService.login(any())).thenReturn(true);

        // Trigger
        ResultActions result = this.mockMvc.perform(get("/whatever"));

        // Assertions
        result.andExpect(
                redirectedUrl(viewId(Views.LOGIN))
        );
    }

    @Test
    @SneakyThrows
    void testAutenticazione() {
        // Context
        String email = "user";
        LoginModel model = new LoginModel();
        model.setUsername(email);
        when(authenticationService.login(email)).thenReturn(true);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/" + viewId(Views.LOGIN))
                        .flashAttr("login", model)
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)));
    }

}