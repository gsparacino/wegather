package it.unimib.wegather.frontend.evento;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.backend.entity.EventoInLocale;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.evento.model.EventoInCasaModel;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.evento.model.EventoInLocaleModel;
import it.unimib.wegather.frontend.evento.model.EventoModel;
import it.unimib.wegather.service.EventoSearchService;
import it.unimib.wegather.service.EventoService;
import it.unimib.wegather.service.UtenteService;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.StubUtils.stubEvento;
import static it.unimib.wegather.StubUtils.stubEventoInLocale;
import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
@Import(UserContextConfiguration.class)
class EventoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @MockBean
    private UtenteService utenteService;

    @MockBean
    private EventoSearchService eventoSearchService;

    @Test
    @SneakyThrows
    void testCreateNewInCasa() {
        // Context
        String username = "johndoe";
        when(utenteService.trovaUtentePerUsername(username))
                .thenReturn(mock(Utente.class));
        EventoInCasaModel request = new EventoInCasaModel();
        request.setNome("Evento di test");
        request.setDescrizione("Descrizione evento");

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/evento/casa")
                        .flashAttr("evento", request)
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)))
        ;
    }

    @Test
    @SneakyThrows
    void testCreateNewInLocale() {
        // Context
        String username = "johndoe";
        when(utenteService.trovaUtentePerUsername(username))
                .thenReturn(mock(Utente.class));
        EventoInLocaleModel request = new EventoInLocaleModel();
        request.setNome("Evento di test");
        request.setDescrizione("Descrizione evento");

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/evento/locale")
                        .flashAttr("evento", request)
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)))
        ;
    }

    @Test
    @SneakyThrows
    void testGetEventoById() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        when(eventoService.trovaEvento(idEvento)).thenReturn(stubEventoInLocale(idEvento));
        when(utenteService.trovaUtentePerUsername(username))
                .thenReturn(mock(Utente.class));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/evento/" + idEvento)
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.EVENTO.name().toLowerCase()))
        ;
    }

    @Test
    @SneakyThrows
    void testGetEvento() {
        // Context
        String username = "johndoe";
        when(utenteService.trovaUtentePerUsername(username))
                .thenReturn(mock(Utente.class));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/evento")
                        .queryParam("tipo", "casa")
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.EVENTO.name().toLowerCase()))
                .andExpect(model().attributeExists("eventiOrganizzati"))
        ;
    }

    @Test
    @SneakyThrows
    void testDeleteEvento() {
        // Context
        String username = "johndoe";
        EventoModel model = new EventoModel();
        model.setId(1L);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/evento/delete")
                        .flashAttr("evento", model)
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.LISTA)))
        ;
    }

    @Test
    @SneakyThrows
    void testDeleteEventoWarning() {
        // Context
        String username = "johndoe";
        long idEvento = 1L;
        EventoInLocale evento = stubEventoInLocale(idEvento);
        when(eventoService.trovaEvento(idEvento)).thenReturn(evento);

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/evento/" + idEvento + "/delete")
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(Views.EVENTO_DELETE.name().toLowerCase()))
                .andExpect(model().attributeExists("evento"))
        ;
    }

}