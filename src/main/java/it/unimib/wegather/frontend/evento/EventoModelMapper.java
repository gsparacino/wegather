package it.unimib.wegather.frontend.evento;

import it.unimib.wegather.frontend.evento.model.EventoInCasaModel;
import it.unimib.wegather.frontend.evento.model.EventoInLocaleModel;
import it.unimib.wegather.frontend.evento.model.EventoModel;
import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.EventoInCasa;
import it.unimib.wegather.backend.entity.EventoInLocale;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
class EventoModelMapper {

    static EventoInCasa map(@NonNull EventoInCasaModel model) {
        EventoInCasa entity = new EventoInCasa();
        entity.setNome(model.getNome());
        entity.setDescrizione(model.getDescrizione());
        entity.setId(model.getId());
        Optional.ofNullable(model.getEventoPrecedente())
                .filter(EventoModelMapper::isNotNone)
                .map(EventoModelMapper::mapEventoPrecedente)
                .ifPresent(entity::setEventoPrecedente);
        entity.setCitofono(model.getCitofono());
        entity.setIndirizzo(model.getIndirizzo());
        return entity;
    }


    /**
     * Maps EventoInLocaleModel to EventoInLocale
     */
    static EventoInLocale map(@NonNull EventoInLocaleModel model) {
        EventoInLocale entity = new EventoInLocale();
        entity.setNome(model.getNome());
        entity.setDescrizione(model.getDescrizione());
        entity.setIndirizzo(model.getIndirizzo());
        entity.setId(model.getId());
        Optional.ofNullable(model.getEventoPrecedente())
                .filter(EventoModelMapper::isNotNone)
                .map(EventoModelMapper::mapEventoPrecedente)
                .ifPresent(entity::setEventoPrecedente);
        entity.setLocale(model.getLocale());
        entity.setCosto(model.getCosto());
        return entity;
    }

    static List<EventoModel> map(List<Evento> model) {
        return Optional.ofNullable(model)
                .stream().flatMap(Collection::stream)
                .map(EventoModelMapper::map)
                .collect(Collectors.toList());
    }

    static EventoModel map(@NonNull Evento entity) {
        if (entity instanceof EventoInCasa) {
            EventoInCasa eventoInCasa = (EventoInCasa) entity;
            return map(eventoInCasa);
        } else if (entity instanceof EventoInLocale) {
            EventoInLocale eventoInLocale = (EventoInLocale) entity;
            return map(eventoInLocale);
        }

        throw new IllegalArgumentException("Tipologia di entity non supportata");
    }

    static EventoInCasaModel map(@NonNull EventoInCasa entity) {
        EventoInCasaModel model = new EventoInCasaModel();
        model.setId(entity.getId());
        model.setNome(entity.getNome());
        model.setDescrizione(entity.getDescrizione());
        Optional.ofNullable(entity.getEventoPrecedente())
                .map(EventoModelMapper::map)
                .ifPresent(model::setEventoPrecedente);
        model.setCitofono(entity.getCitofono());
        model.setIndirizzo(entity.getIndirizzo());
        return model;
    }


    /**
     * maps EventoInLocale to EventInLocaleModel
     */
    static EventoInLocaleModel map(@NonNull EventoInLocale entity) {
        EventoInLocaleModel model = new EventoInLocaleModel();
        model.setId(entity.getId());
        model.setNome(entity.getNome());
        model.setDescrizione(entity.getDescrizione());
        model.setIndirizzo(entity.getIndirizzo());
        Optional.ofNullable(entity.getEventoPrecedente())
                .map(EventoModelMapper::map)
                .ifPresent(model::setEventoPrecedente);
        model.setCosto(entity.getCosto());
        model.setLocale(entity.getLocale());
        return model;
    }


    private static boolean isNotNone(EventoModel eventoModel) {
        return !Objects.equals(-1L, eventoModel.getId());
    }

    private static Evento mapEventoPrecedente(EventoModel eventoModel) {
        Evento result = new Evento();
        result.setId(eventoModel.getId());
        return result;
    }

}
