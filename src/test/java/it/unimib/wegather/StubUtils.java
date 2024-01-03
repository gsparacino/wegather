package it.unimib.wegather;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.EventoInLocale;
import it.unimib.wegather.backend.entity.Proposta;
import it.unimib.wegather.backend.entity.Utente;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class StubUtils {

    //Stub single Event
    public static Evento stubEvento() {
        Evento evento = new Evento();
        evento.setNome("Evento di prova");
        evento.setDescrizione("Descrizione evento uno");
        return evento;
    }

    //Stub single Event specified id
    public static Evento stubEvento(Long id) {
        Evento evento = stubEvento();
        evento.setId(id);
        return evento;
    }

    public static EventoInLocale stubEventoInLocale(Long id) {
        EventoInLocale evento = new EventoInLocale();
        evento.setId(id);
        evento.setLocale("locale");
        evento.setNome("Evento in Locale");
        evento.setDescrizione("Descrizione evento in locale");
        evento.setCosto(1.00);
        evento.setIndirizzo("indirizzo");
        evento.getProposte().add(stubProposta(2L, null));
        evento.getInvitati().add(stubUtente());
        return evento;
    }

    //Stub single Utente
    public static Utente stubUtente() {
        Utente utente = new Utente();
        utente.setUsername("username");
        utente.setNome("Nome");
        utente.setCognome("Cognome");
        utente.setEmail("email@email.com");
        utente.setTelefono("123456789");
        return utente;
    }

    public static Utente stubUtente(Long id) {
        Utente utente = stubUtente();
        utente.setId(id);
        return utente;
    }

    public static Utente stubUtente(Long id, String username) {
        Utente utente = stubUtente(id);
        utente.setUsername(username);
        return utente;
    }

    public static Proposta stubProposta(Long id, Evento evento) {
        Proposta proposta = new Proposta();
        proposta.setId(id);
        proposta.setData(LocalDate.now());
        proposta.setEvento(evento);
        return proposta;
    }

    //Stub List Utente
    public static List<Utente> stubUtenti() {
        List<Utente> utenti = new ArrayList<Utente>();
        Utente utente1 = new Utente();
        utente1.setUsername("username");
        utente1.setNome("Nome");
        utente1.setCognome("Cognome");
        utente1.setEmail("email@email.com");
        utente1.setTelefono("123456789");
        utenti.add(utente1);

        Utente utente2 = new Utente();
        utente2.setUsername("username2");
        utente2.setNome("Nome2");
        utente2.setCognome("Cognome2");
        utente2.setEmail("email2@email.com");
        utente2.setTelefono("223456789");
        utenti.add(utente2);

        return utenti;
    }
}
