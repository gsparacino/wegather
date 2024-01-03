package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.EventoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * {@link Service} che gestisce gli <i>use case</i> legati ad un {@link Evento}
 */
@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    /**
     * Salva un evento. Se il campo <code>id</code> dell'evento fornito in input è valorizzato
     * aggiorna l'evento corrispondente, altrimenti ne crea uno nuovo.
     *
     * @param organizzatore l'{@link Utente} che organizza l'evento
     * @param evento        l'{@link Evento} da salvare
     * @return l'{@link Evento} salvato
     */
    public Evento salvaEvento(@NonNull Utente organizzatore,
                              @NonNull Evento evento) {
        evento.setOrganizzatore(organizzatore);
        copiaInvitatiDaEventoPrecedente(evento);

        if (isNull(evento.getId())) {
            return creaEvento(evento);
        } else {
            return aggiornaEvento(evento);
        }
    }

    /**
     * Imposta l'elenco d'invitati a un evento.
     *
     * @param invitati la lista di elementi {@link Utente} che rappresenta gli invitati all'evento
     * @param evento   l'{@link Evento} da aggiornare
     * @return l'{@link Evento} aggiornato
     */
    public Evento salvaInvitati(@NonNull List<Utente> invitati,
                                @NonNull Evento evento) {
        evento.setInvitati(invitati);
        return eventoRepository.saveAndFlush(evento);
    }

    /**
     * Trova un {@link Evento} in base al suo {@code id}
     *
     * @param id l'<code>id</code> dell'{@link Evento} da cercare
     * @return Se l'{@link Evento} con {@id empty}
     */
    public Evento trovaEvento(@NonNull Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'evento con ID " + id + " non è presente a sistema"));
    }

    /**
     * Cancella un {@link Evento}
     *
     * @param id l'<code>id</code> dell'{@link Evento} da cancellare
     */
    public void cancellaEvento(@NonNull Long id) {
        eventoRepository.deleteById(id);
    }

    private Evento creaEvento(@NonNull Evento evento) {
        return eventoRepository.saveAndFlush(evento);
    }

    /**
     *  Aggiorna l'{@link Evento} esistente
     * @param evento {@link Evento} aggiornato
     * @return l'{@link Evento} appena aggiornato
     */
    private Evento aggiornaEvento(@NonNull Evento evento) {
        Long id = evento.getId();
        Evento entity = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'evento con ID " + id + " non è presente a sistema."));
        entity.setNome(evento.getNome());
        entity.setDescrizione(evento.getDescrizione());
        entity.setIndirizzo(evento.getIndirizzo());
        return eventoRepository.saveAndFlush(entity);
    }

    private void copiaInvitatiDaEventoPrecedente(@NonNull Evento evento) {
        Evento eventoPrecedente = evento.getEventoPrecedente();
        if (!isNull(eventoPrecedente)) {
            List<Utente> invitati = caricaInvitatiDaEventoPrecedente(eventoPrecedente);
            evento.setInvitati(invitati);
        }
    }

    private List<Utente> caricaInvitatiDaEventoPrecedente(@NonNull Evento eventoPrecedente) {
        Evento precedente = trovaEvento(eventoPrecedente.getId());
        return Optional.ofNullable(precedente.getInvitati())
                .stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
