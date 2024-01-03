package it.unimib.wegather.frontend.invitati;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.backend.entity.EventoInLocale;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.utente.UtenteController;
import it.unimib.wegather.frontend.utente.model.UtenteModel;
import it.unimib.wegather.service.EventoService;
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

import java.util.Arrays;
import java.util.List;

import static it.unimib.wegather.StubUtils.*;
import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvitatiController.class)
@Import(UserContextConfiguration.class)
class InvitatiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteService utenteService;

    @MockBean
    private EventoService eventoService;


    @Test
    @SneakyThrows
    void testGetInvitati() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        EventoInLocale evento = stubEventoInLocale(idEvento);
        when(eventoService.trovaEvento(idEvento)).thenReturn(evento);
        when(utenteService.cercaUtentiNonInvitatiAdEvento(evento)).thenReturn(List.of(stubUtente()));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/invitati")
                        .param("id", String.valueOf(idEvento))
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.PARTECIPANTI.name().toLowerCase()))
                .andExpect(model().attributeExists("evento"))
                .andExpect(model().attributeExists("utentiInvitati"))
                .andExpect(model().attributeExists("utentiNonInvitati"))
        ;
    }

    @Test
    @SneakyThrows
    void testPostInvitati() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        EventoInLocale evento = stubEventoInLocale(idEvento);
        when(eventoService.trovaEvento(idEvento)).thenReturn(evento);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/invitati")
                        .param("idEvento", String.valueOf(idEvento))
                        .param("idInvitati", String.valueOf(1L))
                        .param("idInvitati", String.valueOf(2L))
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)))
        ;
    }

}