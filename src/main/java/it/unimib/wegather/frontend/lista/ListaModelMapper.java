package it.unimib.wegather.frontend.lista;

import it.unimib.wegather.backend.entity.*;
import it.unimib.wegather.frontend.lista.model.EventiModel;
import it.unimib.wegather.frontend.lista.model.EventoModel;
import it.unimib.wegather.frontend.lista.model.OrganizzatoreModel;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.size;

@UtilityClass
class ListaModelMapper {

    static EventiModel map(@NonNull List<Evento> organizzatore,
                           @NonNull List<Evento> partecipante) {
        EventiModel model = new EventiModel();
        model.setOrganizzatore(map(organizzatore));
        model.setPartecipante(map(partecipante));
        return model;
    }

    static EventoModel map(@NonNull Evento evento) {
        EventoModel model = new EventoModel();
        if (evento instanceof EventoInCasa) {
            model.setTipo("Evento in Casa");
        } else if (evento instanceof EventoInLocale) {
            model.setTipo("Evento in Locale");
        }
        model.setId(evento.getId());
        model.setNome(evento.getNome());
        model.setDescrizione(evento.getDescrizione());
        model.setPartecipanti(size(evento.getInvitati()));
        model.setOrganizzatore(map(evento.getOrganizzatore()));
        model.setProposte(mapProposte(evento.getProposte()));
        return model;
    }

    private static List<LocalDate> mapProposte(List<Proposta> proposte) {
        return Optional.ofNullable(proposte)
                .stream().flatMap(Collection::stream)
                .map(Proposta::getData)
                .collect(Collectors.toList());
    }

    private static List<EventoModel> map(@NonNull List<Evento> eventi) {
        return eventi.stream()
                .map(ListaModelMapper::map)
                .collect(Collectors.toList());
    }

    private static OrganizzatoreModel map(@NonNull Utente utente) {
        OrganizzatoreModel model = new OrganizzatoreModel();
        model.setId(utente.getId());
        model.setUsername(utente.getUsername());
        return model;
    }
}
