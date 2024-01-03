package it.unimib.wegather.frontend.proposta;


import it.unimib.wegather.frontend.Views;
import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Proposta;
import it.unimib.wegather.frontend.proposta.model.PropostaModel;
import it.unimib.wegather.service.EventoService;
import it.unimib.wegather.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static it.unimib.wegather.frontend.ControllerUtils.redirectWithQueryParam;
import static it.unimib.wegather.frontend.ControllerUtils.viewId;
import static it.unimib.wegather.frontend.proposta.PropostaMapper.map;


@Controller
@RequiredArgsConstructor
public class PropostaController {
    private final EventoService eventoService;
    private final PropostaService propostaService;


    @PostMapping("/addProposta")
    public String addEventoProposte(Long id,String date,
                                    Model model) {
        Evento entity = eventoService.trovaEvento(id);

        List<Proposta> proposte =  entity.getProposte();
        Proposta nuovaEntity = new Proposta();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.ITALIAN );

        LocalDate dateEntity = LocalDate.parse(date, formatter);
        nuovaEntity.setData(dateEntity);
        proposte.add(nuovaEntity);
        propostaService.setProposteEvento(proposte,entity);

        return redirectWithQueryParam(Views.PROPOSTE, entity.getId());
    }

    @GetMapping("/removeProposta")
    public String removeEventoProposte(Long idProposta, Long idEvento,
                                    Model model) {

        propostaService.deleteProposta(idEvento,idProposta);


        return redirectWithQueryParam(Views.PROPOSTE, idEvento);
    }


    @GetMapping("/proposte")
    public String getEventoProposte(Long id,
                                        Model model) {
        Evento entity = eventoService.trovaEvento(id);
        model.addAttribute("evento", entity);

        List<Proposta> proposteEntity = entity.getProposte();
        List<PropostaModel> proposte = map(proposteEntity);

        model.addAttribute("proposte", proposte);
        return viewId(Views.PROPOSTE);
    }


}
