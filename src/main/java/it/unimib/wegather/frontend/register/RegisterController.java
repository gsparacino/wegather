package it.unimib.wegather.frontend.register;

import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.register.model.RegisterModel;
import it.unimib.wegather.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static it.unimib.wegather.frontend.ControllerUtils.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AuthenticationService authenticationService;



    /**
     * Predispone la view di login, inizializzando il model che sarà riempito dall'utente tramite il form di autenticazione
     *   public String getRegister(Model model) {
     */
    @GetMapping("/register")
    public String getRegister(Model model,
                              @RequestParam("username") String username) {
        RegisterModel rm = new RegisterModel();
        rm.setUsername(username);
        model.addAttribute("register", rm);
        return viewId(Views.REGISTER);
    }

    /**
     * Gestisce l'autenticazione dell'utente
     *
     * @param register    il Model della view di register, contenente i valori immessi dall'utente nel form di registrazione
     * @param response la response da restituire, a cui verrà eventualmente aggiunto un cookie di sessione per
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute(name = "register") RegisterModel register,
                            @NonNull HttpServletResponse response) {
        final String email = register.getEmail();
        final String username = register.getUsername();
        if(authenticationService.register(email,username)){
            response.addCookie(new Cookie("username", username));
            return redirect(Views.LISTA);
        }else {
            return redirectWithData(Views.REGISTER,username);
        }

    }

}

