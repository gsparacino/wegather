package it.unimib.wegather.frontend.proposta;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.utente.UtenteController;
import it.unimib.wegather.frontend.utente.model.UtenteModel;
import it.unimib.wegather.service.EventoService;
import it.unimib.wegather.service.PropostaService;
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

import static it.unimib.wegather.StubUtils.stubEventoInLocale;
import static it.unimib.wegather.StubUtils.stubUtente;
import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.redirectWithQueryParam;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropostaController.class)
@Import(UserContextConfiguration.class)
class PropostaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @MockBean
    private PropostaService propostaService;

    @Test
    @SneakyThrows
    void testAddEventoProposta() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        when(eventoService.trovaEvento(idEvento)).thenReturn(stubEventoInLocale(idEvento));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/addProposta")
                        .param("id", String.valueOf(idEvento))
                        .param("date", "2023-03-21")
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirectWithQueryParam(Views.PROPOSTE, idEvento)))
        ;
    }

    @Test
    @SneakyThrows
    void testRemoveProposta() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/removeProposta")
                        .param("idEvento", String.valueOf(idEvento))
                        .param("idProposta", String.valueOf(2L))
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirectWithQueryParam(Views.PROPOSTE, idEvento)))
        ;
    }

    @Test
    @SneakyThrows
    void testGetProposte() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        when(eventoService.trovaEvento(idEvento)).thenReturn(stubEventoInLocale(idEvento));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/proposte")
                        .param("id", String.valueOf(idEvento))
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.PROPOSTE.name().toLowerCase()))
                .andExpect(model().attributeExists("proposte"))
        ;
    }

}