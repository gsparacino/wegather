package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.EventoRepository;
import it.unimib.wegather.backend.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static it.unimib.wegather.StubUtils.stubEvento;
import static it.unimib.wegather.StubUtils.stubUtente;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
class EventoSearchServiceTest {

    @MockBean
    private UtenteRepository utenteRepository;
    @MockBean
    private EventoRepository eventoRepository;

    @Test
    void searchEventiByOrganizzatore() {
        // Context
        EventoSearchService service = new EventoSearchService(utenteRepository, eventoRepository);
        final String username = "test";
        Utente organizzatore = stubUtente(1L);
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.of(organizzatore));
        List<Evento> expected = List.of(stubEvento(2L), stubEvento(3L));
        when(eventoRepository.findByOrganizzatoreId(organizzatore.getId())).thenReturn(expected);

        // Trigger
        List<Evento> actual = service.searchEventiByOrganizzatore(username);

        // Assertions
        assertThat(actual, is(expected));
    }

    @Test
    void searchEventiByOrganizzatoreNotFound() {
        // Context
        EventoSearchService service = new EventoSearchService(utenteRepository, eventoRepository);
        final String username = "test";
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Trigger
        List<Evento> actual = service.searchEventiByOrganizzatore(username);

        // Assertions
        assertThat(actual, is(empty()));
    }

    @Test
    void searchEventiByPartecipante() {
        // Context
        EventoSearchService service = new EventoSearchService(utenteRepository, eventoRepository);
        final String username = "test";
        Utente partecipante = stubUtente(1L);
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.of(partecipante));
        List<Evento> expected = List.of(stubEvento(2L), stubEvento(3L));
        when(eventoRepository.findByInvitatiId(partecipante.getId())).thenReturn(expected);

        // Trigger
        List<Evento> actual = service.searchEventiByPartecipante(username);

        // Assertions
        assertThat(actual, is(expected));
    }

    @Test
    void searchEventiByPartecipanteNotFound() {
        // Context
        EventoSearchService service = new EventoSearchService(utenteRepository, eventoRepository);
        final String username = "test";
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Trigger
        List<Evento> actual = service.searchEventiByPartecipante(username);

        // Assertions
        assertThat(actual, is(empty()));
    }
}