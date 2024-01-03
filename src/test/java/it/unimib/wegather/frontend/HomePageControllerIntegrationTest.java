package it.unimib.wegather.frontend;

import it.unimib.wegather.authentication.UserContextConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomePageController.class)
@Import(UserContextConfiguration.class)
class HomePageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void testGetRootRedirect() {
        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/")
                        .cookie(new Cookie("username", "whatever"))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)));
    }

}