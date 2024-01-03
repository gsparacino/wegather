package it.unimib.wegather.frontend.lista;

import it.unimib.wegather.authentication.UserContext;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.lista.model.EventiModel;
import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.service.EventoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static it.unimib.wegather.frontend.lista.ListaModelMapper.map;

@Controller
@RequiredArgsConstructor
public class ListaController {

    private final EventoSearchService eventoSearchService;

    private final UserContext userContext;

    /**
     * Predispone la view con la lista degli eventi dell'utente
     *
     */
    @GetMapping("/lista")
    public String getLista(Model model) {
        String username = userContext.getUsername();
        model.addAttribute("nome", username);
        List<Evento> organizzatore = eventoSearchService.searchEventiByOrganizzatore(username);
        List<Evento> partecipante = eventoSearchService.searchEventiByPartecipante(username);
        EventiModel eventiModel = map(organizzatore, partecipante);
        model.addAttribute("eventi", eventiModel);
        return viewId(Views.LISTA);
    }


}
