package it.unimib.wegather.frontend.utente;

import it.unimib.wegather.authentication.UserContext;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.utente.model.UtenteModel;
import it.unimib.wegather.service.UtenteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    private final UserContext userContext;

    /**
     * Predispone la view con la lista degli eventi dell'utente
     */
    @GetMapping("/utente")
    public String getUtente(Model model) {
        Utente utente = utenteService.trovaUtentePerUsername(userContext.getUsername());

        UtenteModel utenteModel = UtenteMapper.map(utente);
        model.addAttribute("utente", utenteModel);
        model.addAttribute("username", utenteModel.getUsername());
        return viewId(Views.UTENTE);
    }

    @PostMapping("/utente/delete")
    public String deleteUtente(@ModelAttribute(name = "utente") UtenteModel utenteModel,
                               @NonNull HttpServletResponse response
    ) {
        utenteService.cancellaUtente(utenteModel.getId());
        Cookie cookie = new Cookie("username", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return redirect(Views.LOGIN);
    }
}
