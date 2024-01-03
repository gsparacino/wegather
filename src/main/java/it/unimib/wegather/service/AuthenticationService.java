package it.unimib.wegather.service;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.backend.repository.UtenteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UtenteRepository utenteRepository;


    /**
     * Effettua la login per l'{@link Utente}.
     * Se l'{@code username} non corrisponde a nessun {@link Utente} ritorna false.
     *
     * @param username l'{@code username} dell'{@link Utente}
     * @return {@code true} se la login ha avuto successo, {@code false} altrimenti
     */
    public boolean login(@NonNull String username) {
        if(utenteRepository.findByUsername(username).isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Registra il nuovo {@link Utente}
     * @param email l'{@code email} dell'{@link Utente} da creare
     * @param username l'{@code username} dell'{@link Utente} da creare
     * @return {@code true} se la registrazione ha avuto successo, {@code false} altrimenti
     */
    public boolean register(@NotNull String email, @NotNull String username){
        if(utenteRepository.findByUsername(username).isEmpty()) {
            Utente utenteRegistrato = creaUtente(email,username);
            if(utenteRegistrato.getId() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra un nuovo {@link Utente} con l'{@code email} specificata
     * @param email l'{@code email}
     * @param username l'{@code username}
     * @return @link Utente} creato
     */
    private Utente creaUtente(String email, String username) {
        Utente utente = new Utente();
        utente.setUsername(username);
        utente.setEmail(email);
        return utenteRepository.saveAndFlush(utente);
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

}
