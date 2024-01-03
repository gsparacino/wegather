package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static it.unimib.wegather.StubUtils.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//Tests EventoService
@ExtendWith(SpringExtension.class)
class EventoServiceTest {

    @MockBean
    private EventoRepository eventoRepository;

    @Test
    void creaEvento() {
        // Context
        EventoService service = new EventoService(eventoRepository);

        // Trigger
        service.salvaEvento(stubUtente(), stubEvento());

        // Assertions
        Evento expected = stubEvento();
        expected.setOrganizzatore(stubUtente());
        verify(eventoRepository).saveAndFlush(expected);
    }

    @Test
    void aggiornaEvento() {
        // Context
        EventoService service = new EventoService(eventoRepository);
        long idEvento = 1L;
        Evento eventoEsistente = new Evento(idEvento, "Evento esistente", "Desc", null, null, null, null,null);
        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoEsistente));
        Evento eventoInput = stubEvento(idEvento);
        Utente organizzatoreInput = stubUtente();

        // Trigger
        service.salvaEvento(organizzatoreInput, eventoInput);

        // Assertions
        Evento expected = createExpected(eventoEsistente, eventoInput, organizzatoreInput);
        verify(eventoRepository).findById(idEvento);
        verify(eventoRepository).saveAndFlush(expected);
    }

    private static Evento createExpected(Evento eventoEsistente, Evento eventoInput, Utente organizzatoreInput) {
        eventoEsistente.setOrganizzatore(organizzatoreInput);
        eventoEsistente.setDescrizione(eventoInput.getDescrizione());
        eventoEsistente.setNome(eventoInput.getNome());
        eventoEsistente.setProposte(List.of(stubProposta(1L, eventoEsistente)));
        return eventoEsistente;
    }

    private static Evento createExpected(Evento eventoEsistente, Evento eventoInput, Utente organizzatoreInput, List<Utente> invitati) {
        eventoEsistente = createExpected(eventoEsistente,eventoInput,organizzatoreInput);
        eventoEsistente.setInvitati(invitati);
        return eventoEsistente;
    }



    @Test
    void cancellaEvento(){
        // Context
        EventoService service = new EventoService(eventoRepository);
        long idEvento = 1L;
        Evento eventoEsistente = new Evento(idEvento, "Evento esistente", "Desc", null, null, null, null,null);
        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoEsistente));
        Evento eventoInput = stubEvento(idEvento);
        Utente organizzatoreInput = stubUtente();

        // Trigger
        service.cancellaEvento(idEvento);

        // Assertions
        verify(eventoRepository).deleteById(idEvento);
    }


    @Test
    void trovaEvento(){
        // Context
        EventoService service = new EventoService(eventoRepository);
        long idEvento = 1L;
        Evento eventoEsistente = new Evento(idEvento, "Evento esistente", "Desc", null, null, null, null,null);
        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoEsistente));
        Evento eventoInput = stubEvento(idEvento);
        Utente organizzatoreInput = stubUtente();

        // Trigger
        service.trovaEvento(idEvento);

        //Assertion
        verify(eventoRepository).findById(idEvento);
    }

    @Test
    void salvaInvitati(){
        // Context
        EventoService service = new EventoService(eventoRepository);
        long idEvento = 1L;
        Utente organizzatoreInput = stubUtente();
        List<Utente> invitati = stubUtenti();
        Evento eventoInput = stubEvento(idEvento);
        eventoInput.setOrganizzatore(organizzatoreInput);
        Evento eventoEsistente = new Evento(idEvento, "Evento esistente", "Desc", null, null, null, null,null);
        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoEsistente));

        // Trigger
        service.salvaInvitati(invitati, eventoInput);

        // Assertions
        verify(eventoRepository).saveAndFlush(eventoInput);
        verify(eventoRepository).saveAndFlush(argThat(evento -> evento.getInvitati().equals(invitati)));
    }

}