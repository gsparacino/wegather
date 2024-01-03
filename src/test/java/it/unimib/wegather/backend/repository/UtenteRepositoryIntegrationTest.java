package it.unimib.wegather.backend.repository;

import it.unimib.wegather.backend.entity.Utente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@Sql("/sql/data.sql")
class UtenteRepositoryIntegrationTest {

    @Autowired
    private UtenteRepository repository;

    @Test
    void testUsernameNotFound() {
        // Trigger
        Optional<Utente> user = repository.findByUsername("notexistingusername");

        // Assertions
        assertThat(user.isEmpty(), is(true));
    }

    @Test
    void testUsernameFound() {
        // Trigger
        Optional<Utente> user = repository.findByUsername("johndoe");

        // Assertions
        assertThat(user.isPresent(), is(true));
    }

}