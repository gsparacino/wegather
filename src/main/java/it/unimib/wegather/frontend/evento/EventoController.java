package it.unimib.wegather.frontend.evento;

import it.unimib.wegather.authentication.UserContext;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.evento.model.EventoInCasaModel;
import it.unimib.wegather.frontend.evento.model.EventoInLocaleModel;
import it.unimib.wegather.frontend.evento.model.EventoModel;
import it.unimib.wegather.frontend.evento.model.TipoEventoModel;
import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.EventoInCasa;
import it.unimib.wegather.backend.entity.EventoInLocale;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.service.EventoSearchService;
import it.unimib.wegather.service.EventoService;
import it.unimib.wegather.service.UtenteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static it.unimib.wegather.frontend.evento.EventoModelMapper.map;
import static it.unimib.wegather.frontend.evento.model.TipoEventoModel.formValue;

@Controller
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final EventoSearchService eventoSearchService;
    private final UtenteService utenteService;

    private final UserContext userContext;

    private static final String eventoAttributeName = "evento";

    /**
     * Mostra una pagina con un form per la creazione di un nuovo evento
     */
    @GetMapping("/evento")
    public String evento(@RequestParam("tipo") String tipo,
                         Model model) {
        inizializzaEventoModel(tipo, model);
        List<Evento> eventiOrganizzati = eventoSearchService.searchEventiByOrganizzatore(userContext.getUsername());
        List<EventoModel> eventiOrganizzatiModel = map(eventiOrganizzati);
        model.addAttribute("eventiOrganizzati", eventiOrganizzatiModel);
        return viewId(Views.EVENTO);
    }

    /**
     * Visualizza i dettagli di un evento
     */
    @GetMapping("/evento/{id}")
    public String getEvento(@PathVariable Long id,
                            Model model) {
        Evento evento = eventoService.trovaEvento(id);
        impostaTipoEvento(model, evento);
        EventoModel eventoModel = map(evento);
        List<Evento> eventiOrganizzati = rimuoviEventoNullo(userContext.getUsername(), id);
        List<EventoModel> eventiOrganizzatiModel = map(eventiOrganizzati);

        model.addAttribute(eventoAttributeName, eventoModel);
        model.addAttribute("eventiOrganizzati", eventiOrganizzatiModel);
        return viewId(Views.EVENTO);
    }

    /**
     * Effettua la cancellazione di un evento e redirige l'utente alla sua lista degli eventi
     *
     * @param eventoModel i dati dell'evento da cancellare
     */
    @PostMapping("/evento/delete")
    public String deleteEvento(@ModelAttribute(name = "evento") EventoModel eventoModel) {
        eventoService.cancellaEvento(eventoModel.getId());
        return redirect(Views.LISTA);
    }

    /**
     * Mostra una pagina di conferma prima di procedere all'eliminazione di un evento
     *
     * @param id l'ID dell'evento che si vuole cancellare
     */
    @GetMapping("/evento/{id}/delete")
    public String getDeleteWarning(@PathVariable Long id,
                                   Model model) {
        Evento evento = eventoService.trovaEvento(id);
        EventoModel eventoModel = map(evento);
        model.addAttribute(eventoAttributeName, eventoModel);
        return viewId(Views.EVENTO_DELETE);
    }

    /**
     * Salva un evento. Se l'evento esiste già, lo aggiorna; se l'evento non esiste, lo crea.
     *
     * @param eventoModel il model aggiornato con i dati inseriti dall'utente
     */
    @PostMapping("/evento/casa")
    public String salvaEventoInCasa(@ModelAttribute(name = "evento") EventoInCasaModel eventoModel) {
        Utente utente = utenteService.trovaUtentePerUsername(userContext.getUsername());
        EventoInCasa entity = map(eventoModel);
        eventoService.salvaEvento(utente, entity);
        return redirect(Views.LISTA);
    }

    /**
     * Salva un evento. Se l'evento esiste già, lo aggiorna; se l'evento non esiste, lo crea.
     *
     * @param eventoModel il model aggiornato con i dati inseriti dall'utente
     */
    @PostMapping("/evento/locale")
    public String salvaEventoInLocale(@ModelAttribute(name = "evento") EventoInLocaleModel eventoModel) {
        Utente utente = utenteService.trovaUtentePerUsername(userContext.getUsername());
        EventoInLocale entity = map(eventoModel);
        eventoService.salvaEvento(utente, entity);
        return redirect(Views.LISTA);
    }

    private void inizializzaEventoModel(@NonNull String tipo, @NonNull Model model) {
        TipoEventoModel tipoEvento = formValue(tipo);
        switch (tipoEvento) {
            case IN_CASA:
                model.addAttribute(eventoAttributeName, new EventoInCasaModel());
                break;
            case IN_LOCALE:
                model.addAttribute(eventoAttributeName, new EventoInLocaleModel());
                break;
        }
        model.addAttribute("tipo", tipo);
    }

    private List<Evento> rimuoviEventoNullo(String username, Long id) {
        return eventoSearchService.searchEventiByOrganizzatore(username)
                .stream()
                .filter(evento1 -> !Objects.equals(id, evento1.getId()))
                .collect(Collectors.toList());
    }

    private void impostaTipoEvento(Model model, Evento evento) {
        if (evento instanceof EventoInLocale) {
            model.addAttribute("tipo", TipoEventoModel.IN_LOCALE.getId());
        } else if (evento instanceof EventoInCasa) {
            model.addAttribute("tipo", TipoEventoModel.IN_CASA.getId());
        }
    }

}
