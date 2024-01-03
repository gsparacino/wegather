package it.unimib.wegather.frontend.utente;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.utente.model.UtenteModel;
import it.unimib.wegather.service.UtenteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.StubUtils.stubUtente;
import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UtenteController.class)
@Import(UserContextConfiguration.class)
class UtenteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteService utenteService;

    @Test
    @SneakyThrows
    void testGetUtente() {
        // Context
        String username = "johndoe";
        Utente utente = stubUtente(1L, username);
        when(utenteService.trovaUtentePerUsername(username))
                .thenReturn(utente);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/utente")
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.UTENTE.name().toLowerCase()))
                .andExpect(model().attributeExists("utente"))
                .andExpect(model().attribute("username", is(username)))
        ;
    }

    @Test
    @SneakyThrows
    void testDeleteUtente() {
        // Context
        String username = "johndoe";
        long idUtente = 1L;
        UtenteModel model = new UtenteModel();
        model.setId(idUtente);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/utente/delete")
                        .flashAttr("utente", model)
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LOGIN)))
                .andExpect(cookie().maxAge("username", 0))
        ;
    }

}