package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Proposta;
import it.unimib.wegather.backend.repository.EventoRepository;
import it.unimib.wegather.backend.repository.PropostaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.unimib.wegather.StubUtils.stubEvento;
import static it.unimib.wegather.StubUtils.stubProposta;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PropostaServiceTest {

    @MockBean
    private EventoRepository eventoRepository;
    @MockBean
    private PropostaRepository propostaRepository;

    @Test
    void setProposteEvento() {
        // Context
        PropostaService service = new PropostaService(eventoRepository, propostaRepository);
        Evento evento = stubEvento();
        List<Proposta> proposte = List.of(stubProposta(null, null), stubProposta(null, null));
        when(eventoRepository.saveAndFlush(any())).thenReturn(evento);

        // Trigger
        Evento actual = service.setProposteEvento(proposte, evento);

        // Assertions
        verify(eventoRepository).saveAndFlush(evento);
        assertThat(evento.getProposte(), is(proposte));
        assertThat(actual, is(evento));
    }

    @Test
    void getProposteEvento() {
        // Context
        PropostaService service = new PropostaService(eventoRepository, propostaRepository);
        Evento evento = stubEvento(1L);
        List<Proposta> expected = List.of(stubProposta(null, null), stubProposta(null, null));
        when(propostaRepository.findByEventoId(evento.getId())).thenReturn(expected);

        // Trigger
        List<Proposta> actual = service.getProposteEvento(evento);

        // Assertions
        assertThat(actual, is(expected));
    }

    @Test
    void deleteProposta() {
        // Context
        PropostaService service = new PropostaService(eventoRepository, propostaRepository);
        Evento evento = stubEvento(1L);
        Proposta proposta = stubProposta(2L, null);
        List<Proposta> proposte = new ArrayList<>();
        proposte.add(proposta);
        evento.setProposte(proposte);
        when(eventoRepository.findById(evento.getId())).thenReturn(Optional.of(evento));
        when(propostaRepository.getById(proposta.getId())).thenReturn(proposta);

        // Trigger
        service.deleteProposta(evento.getId(), proposta.getId());

        // Assertions
        verify(eventoRepository).saveAndFlush(evento);
        assertThat(evento.getProposte(), not(contains(proposta)));
    }

}