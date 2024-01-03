package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @MockBean
    private UtenteRepository utenteRepository;

    @Test
    void testUtenteNonTrovato() {
        // Context
        AuthenticationService service = new AuthenticationService(utenteRepository);
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Trigger
        boolean actual = service.login("doesntmatter");

        // Assertions
        assertThat(actual, is(false));
        //verify(utenteRepository).saveAndFlush(any(Utente.class));
    }

    @Test
    void testUtenteTrovato() {
        // Context
        String username = "example";
        AuthenticationService service = new AuthenticationService(utenteRepository);
        when(utenteRepository.findByUsername(username)).thenReturn(Optional.of(new Utente()));

        // Trigger
        boolean actual = service.login(username);

        // Assertions
        assertThat(actual, is(true));
    }

}