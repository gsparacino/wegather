package it.unimib.wegather.frontend.invitati;


import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.frontend.invitati.model.UtenteModel;
import it.unimib.wegather.service.EventoService;
import it.unimib.wegather.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static it.unimib.wegather.frontend.invitati.UtenteMapper.map;

@Controller
@RequiredArgsConstructor
public class InvitatiController {
    private final EventoService eventoService;
    private final UtenteService utenteService;

    @GetMapping("/invitati")
    public String getEventoInvitati(@RequestParam("id") Long idEvento,
                                    Model model) {
        Evento entity = eventoService.trovaEvento(idEvento);
        model.addAttribute("evento", entity);

        List<Utente> utentiInvitati = entity.getInvitati();
        List<Utente> utentiNonInvitati = utenteService.cercaUtentiNonInvitatiAdEvento(entity);

        List<UtenteModel> invitati = map(utentiInvitati);
        List<UtenteModel> nonInvitati = map(utentiNonInvitati);

        model.addAttribute("utentiInvitati", invitati);
        model.addAttribute("utentiNonInvitati", nonInvitati);
        return viewId(Views.PARTECIPANTI);
    }


    @PostMapping("/invitati")
    public String postEventoInvitati(Long idEvento, Long[] idInvitati) {
        Evento evento = eventoService.trovaEvento(idEvento);

        List<Utente> invitati = new ArrayList<>();
        if(idInvitati != null) {
            for (Long id : idInvitati) {
                Utente partecipanteCurrent = utenteService.trovaUtentePerId(id);
                invitati.add(partecipanteCurrent);
            }
        }
        eventoService.salvaInvitati(invitati, evento);
        return redirect(Views.LISTA);
    }

}
