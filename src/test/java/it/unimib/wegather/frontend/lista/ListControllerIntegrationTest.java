package it.unimib.wegather.frontend.lista;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.frontend.lista.model.EventoModel;
import it.unimib.wegather.service.EventoSearchService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.StubUtils.stubEvento;
import static it.unimib.wegather.StubUtils.stubUtente;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static it.unimib.wegather.frontend.lista.ListaModelMapper.map;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ListaController.class)
@Import(UserContextConfiguration.class)
class ListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoSearchService eventoSearchService;

    @Test
    @SneakyThrows
    void testUtenteSenzaEventi() {
        // Context
        when(eventoSearchService.searchEventiByOrganizzatore(any())).thenReturn(emptyList());

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/lista")
                        .cookie(new Cookie("username", "whatever"))
        );

        // Assertions
        result
                .andExpect(status().isOk())
                .andExpect(view().name(viewId(Views.LISTA)))
                .andExpect(model().attributeExists("eventi"))
        ;
    }


    @Test
    @SneakyThrows
    void testUtenteConEventi() {
        // Context
        String username = "whatever";
        Evento entity = stubEvento();
        entity.setOrganizzatore(stubUtente());
        when(eventoSearchService.searchEventiByOrganizzatore(username)).thenReturn(singletonList(entity));
        when(eventoSearchService.searchEventiByPartecipante(username)).thenReturn(singletonList(entity));

        // Trigger
        ResultActions result = this.mockMvc.perform(
                get("/lista")
                        .cookie(new Cookie("username", username))
        );

        // Assertions
        EventoModel expectedModel = map(entity);
        result
                .andExpect(status().isOk())
                .andExpect(view().name(viewId(Views.LISTA)))
                .andExpect(model().attributeExists("eventi"))
                .andExpect(model().attribute("eventi", hasProperty("organizzatore", contains(expectedModel))))
                .andExpect(model().attribute("eventi", hasProperty("partecipante", contains(expectedModel))))
        ;
    }

}