package it.unimib.wegather.frontend.info;

import it.unimib.wegather.authentication.UserContextConfiguration;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.info.model.InfoModel;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.service.UtenteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(InfoController.class)
@Import(UserContextConfiguration.class)
class InfoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteService utenteService;

    @Test
    @SneakyThrows
    public void testUpdateUser() {
        // Context
        when(utenteService.editUtente(any(), any(), any(), any(), any())).thenReturn(mock(Utente.class));
        InfoModel input = InfoModel.builder()
                .nome("nome")
                .cognome("cognome")
                .email("email")
                .telefono("telefono")
                .build();
        String username = "example";

        // Trigger
        ResultActions result = this.mockMvc.perform(
                post("/info")
                        .cookie(new Cookie("username", username))
                        .flashAttr("info", input)
        );

        // Assertions
        verify(utenteService)
                .editUtente(username, input.getNome(), input.getCognome(), input.getEmail(), input.getTelefono());

        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(redirect(Views.UTENTE)))
        ;

    }
}