package it.unimib.wegather.service;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.UtenteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * {@link Service} che gestisce gli <i>use case</i> legati ad un {@link Utente}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public List<Utente> cercaUtentiNonInvitatiAdEvento(@NonNull Evento evento) {
        Long idEvento = evento.getId();
        Long idOrganizzatore = evento.getOrganizzatore().getId();
        return utenteRepository.findUtentiNonInvitati(idEvento, idOrganizzatore);
    }

    /**
     * Crea un nuovo {@link Utente}
     *
     * @param username lo {@code username} dell'{@link Utente} da creare
     * @return l'{@link Utente} creato
     */
    public Utente createUtente(@NonNull String username) {
        Utente utente = new Utente();
        utente.setUsername(username);
        return utenteRepository.saveAndFlush(utente);
    }

    /**
     * Cancella un {@link Utente}, dato il suo {@code id}
     *
     * @param id l'<code>id</code> dell'{@link Utente} da cancellare
     */
    public void cancellaUtente(@NonNull Long id) {
        utenteRepository.deleteById(id);
    }

    public Utente editUtente(@NonNull String username, String nome, String cognome, String email, String telefono) {
        Optional<Utente> optionalUtente = utenteRepository.findByUsername(username);
        log.debug("Utente {} presente? {}", username, optionalUtente.isPresent());
        if (optionalUtente.isEmpty()) {
            throw new IllegalArgumentException("Impossibile modificare l'utente " + username + "in quanto non è presente a sistema");
        }
        Utente utente = optionalUtente.get();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        utente.setTelefono(telefono);
        return utenteRepository.saveAndFlush(utente);
    }

    /**
     * Trova un {@link Utente} in base al suo {@code id}
     *
     * @param id l'<code>id</code> dell'{@link Utente} da cercare
     */
    public Utente trovaUtentePerId(@NonNull Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'utente con id " + id + " non è presente a sistema"));
    }

    /**
     * Trova un {@link Utente} in base al suo {@code username}
     *
     * @param username lo <code>username</code> dell'{@link Utente} da cercare
     */
    public Utente trovaUtentePerUsername(@NonNull String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("L'utente " + username + " non è presente a sistema"));
    }

}