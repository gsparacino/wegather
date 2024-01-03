package it.unimib.wegather.frontend.info;

import it.unimib.wegather.authentication.UserContext;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.info.model.InfoModel;
import it.unimib.wegather.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;

/**
 * Controller per la pagina "info"
 */
@Controller
@RequiredArgsConstructor
public class InfoController {

    private final UtenteService utenteService;

    private final UserContext userContext;

    /**
     * Redirige al @{@link InfoController}
     */
    @GetMapping("/info")
    public String getInfo(Model model) {
        model.addAttribute("info", new InfoModel());
        model.addAttribute("username", userContext.getUsername());
        return viewId(Views.INFO);
    }

    /**
     * Gestisce la modifica dei dati dell'utente
     *
     * @param info    il Model della view di info
     */
    @PostMapping("/info")
    public String postInfo(@ModelAttribute(name = "info") InfoModel info, Model model) {
        String username = userContext.getUsername();
        utenteService.editUtente(username, info.getNome(), info.getCognome(), info.getEmail(), info.getTelefono());
        model.addAttribute("username", username);
        return redirect(Views.UTENTE);
    }

}
