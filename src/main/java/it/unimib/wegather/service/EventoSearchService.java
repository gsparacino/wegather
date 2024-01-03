package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.EventoRepository;
import it.unimib.wegather.backend.repository.UtenteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * {@link Service} per le ricerche tra le istanze di {@link Evento}
 */
@Service
@RequiredArgsConstructor
public class EventoSearchService {

    private final UtenteRepository utenteRepository;
    private final EventoRepository eventoRepository;


    /**
     * Ricerca gli eventi organizzati dall'utente
     *
     * @param username lo username dell'utente
     * @return le liste di eventi in cui <code>username</code> è organizzatore.
     */
    public List<Evento> searchEventiByOrganizzatore(@NonNull String username) {
        Optional<Utente> user = utenteRepository.findByUsername(username);

        if (user.isPresent()) {
            Long userId = user.get().getId();
            return eventoRepository.findByOrganizzatoreId(userId);
        }

        return emptyList();
    }

    /**
     * Ricerca gli eventi a cui l'utente partecipa
     *
     * @param username lo username dell'utente
     * @return le liste di eventi in cui <code>username</code> è partecipante.
     */
    public List<Evento> searchEventiByPartecipante(@NonNull String username) {
        Optional<Utente> user = utenteRepository.findByUsername(username);

        if (user.isPresent()) {
            Long userId = user.get().getId();
            return eventoRepository.findByInvitatiId(userId);
        }

        return emptyList();
    }

}
