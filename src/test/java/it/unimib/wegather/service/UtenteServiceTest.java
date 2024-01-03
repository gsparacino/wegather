package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UtenteServiceTest {

    @MockBean
    private UtenteRepository utenteRepository;

    @Test
    void cercaUtentiNonInvitatiAdEvento() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);

        long idEvento = 1L;
        Evento evento = stubEvento(idEvento);
        evento.setOrganizzatore(stubUtente());

        List<Utente> expected = List.of(stubUtente(), stubUtente());
        when(utenteRepository.findUtentiNonInvitati(idEvento, evento.getOrganizzatore().getId()))
                .thenReturn(expected);

        // Trigger
        List<Utente> actual = service.cercaUtentiNonInvitatiAdEvento(evento);

        // Assertions
        assertThat(actual, is(expected));
    }

    @Test
    void createUtente() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        final String username = "johndoe";

        // Trigger
        service.createUtente(username);

        // Assertions
        verify(utenteRepository).saveAndFlush(
                argThat(utente -> utente.getUsername().equalsIgnoreCase(username))
        );
    }

    @Test
    void cancellaUtente() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        final Long idUtente = 1L;

        // Trigger
        service.cancellaUtente(idUtente);

        // Assertions
        verify(utenteRepository).deleteById(idUtente);
    }

    @Test
    void editUtente() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        Utente utente = stubUtente();
        String username = utente.getUsername();
        String nome = "John";
        String cognome = "Doe";
        String email = "joh@doe.com";
        String telefono = "1111";
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.of(utente));
        when(utenteRepository.saveAndFlush(any())).thenReturn(utente);

        // Trigger
        Utente actual = service.editUtente(username, nome, cognome, email, telefono);

        // Assertions
        verify(utenteRepository).findByUsername(username);
        verify(utenteRepository).saveAndFlush(argThat(entity ->
                username.equalsIgnoreCase(entity.getUsername()) &&
                        nome.equalsIgnoreCase(entity.getNome()) &&
                        cognome.equalsIgnoreCase(entity.getCognome()) &&
                        email.equalsIgnoreCase(entity.getEmail()) &&
                        telefono.equalsIgnoreCase(entity.getTelefono())
        ));
        assertThat(actual, is(utente));
    }

    @Test
    void trovaUtentePerId() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        final Long idUtente = 1L;
        Utente expected = stubUtente(idUtente);
        when(utenteRepository.findById(idUtente)).thenReturn(Optional.of(expected));

        // Trigger
        Utente actual = service.trovaUtentePerId(idUtente);

        // Assertions
        verify(utenteRepository).findById(idUtente);
        assertThat(actual, is(expected));
    }

    @Test
    void trovaUtentePerIdNoResults() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        when(utenteRepository.findById(any())).thenReturn(Optional.empty());

        // Trigger & Assertions
        assertThrows(IllegalArgumentException.class, () -> service.trovaUtentePerId(1L));
    }

    @Test
    void trovaUtentePerUsername() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        Utente expected = stubUtente();
        String username = expected.getUsername();
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.of(expected));

        // Trigger
        Utente actual = service.trovaUtentePerUsername(username);

        // Assertions
        verify(utenteRepository).findByUsername(username);
        assertThat(actual, is(expected));
    }

    @Test
    void trovaUtentePerUsernameNoResults() {
        // Context
        UtenteService service = new UtenteService(utenteRepository);
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Trigger & Assertions
        assertThrows(IllegalArgumentException.class, () -> service.trovaUtentePerUsername("abc"));
    }

}