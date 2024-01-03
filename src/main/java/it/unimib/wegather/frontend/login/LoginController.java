package it.unimib.wegather.frontend.login;

import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.login.model.LoginModel;
import it.unimib.wegather.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static it.unimib.wegather.frontend.ControllerUtils.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    /**
     * Predispone la view di login, inizializzando il model che sarà riempito dall'utente tramite il form di autenticazione
     */
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("login", new LoginModel());
        return viewId(Views.LOGIN);
    }

    /**
     * Gestisce l'autenticazione dell'utente
     *
     * @param login    il Model della view di login, contenente i valori immessi dall'utente nel form di autenticazione
     * @param response la response da restituire, a cui verrà eventualmente aggiunto un cookie di sessione per
     */
    @PostMapping("/login")
    public String postLogin(@ModelAttribute(name = "login") LoginModel login,
                            @NonNull HttpServletResponse response) {
        final String username = login.getUsername();
        if (authenticationService.login(username)) {
            response.addCookie(new Cookie("username", username));
            return redirect(Views.LISTA);
        } else {
            return redirectWithData(Views.REGISTER,username);
        }

    }

}

