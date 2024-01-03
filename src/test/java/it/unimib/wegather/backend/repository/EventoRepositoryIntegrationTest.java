package it.unimib.wegather.backend.repository;

import it.unimib.wegather.backend.entity.Evento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@Sql("/sql/data.sql")
class EventoRepositoryIntegrationTest {

    @Autowired
    private EventoRepository repository;

    @Test
    void testSearchByOrganizzatore() {
        // Trigger
        List<Evento> eventi = repository.findByOrganizzatoreId(1L);

        // Assertions
        assertThat(eventi.size(), is(1));
        assertThat(eventi.get(0).getId(), is(1L));
    }

    @Test
    void testSearchByOrganizzatoreNessunRisultato() {
        // Trigger
        List<Evento> eventi = repository.findByOrganizzatoreId(3L);

        // Assertions
        assertThat(eventi.size(), is(0));

    }

    @Test
    void testSearchByOrganizzatoreUtenteNonEsistente() {
        // Trigger
        List<Evento> eventi = repository.findByOrganizzatoreId(999L);

        // Assertions
        assertThat(eventi.size(), is(0));
    }

    @Test
    void testSearchByInvitato() {
        // Trigger
        List<Evento> eventi = repository.findByInvitatiId(1L);

        // Assertions
        assertThat(eventi.size(), is(1));
        assertThat(eventi.get(0).getId(), is(2L));
    }

    @Test
    void testSearchByInvitatoNessunRisultato() {
        // Trigger
        List<Evento> eventi = repository.findByInvitatiId(2L);

        // Assertions
        assertThat(eventi.size(), is(0));
    }

    @Test
    void testSearchByInvitatoUtenteNonEsistente() {
        // Trigger
        List<Evento> eventi = repository.findByInvitatiId(999L);

        // Assertions
        assertThat(eventi.size(), is(0));
    }

}