package it.unimib.wegather.backend.repository;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    /**
     * Ricerca un {@link Utente} in base al valore della colonna <code>username</code>
     *
     * @param username lo <code>username</code> dell'utente da cercare
     */
    Optional<Utente> findByUsername(String username);

    /**
     * Ricerca un {@link Utente} in base al valore della colonna <code>email</code>
     *
     * @param email l'o '<code>email</code> dell'utente da cercare
     */
    Optional<Utente> findByEmail(String email);

    /**
     * Ritorna la lista di {@link Utente} che sono stati invitati ad un {@link Evento}
     *
     * @param idEvento l'<code>id</code> dell'evento
     */
    List<Utente> findByInvitiId(@NonNull Long idEvento);

    /**
     * Ritorna la lista di {@link Utente} che <b>NON</b> sono stati invitati ad un {@link Evento}
     *
     * @param idEvento l'<code>id</code> dell'evento
     */
    @Query(value =
            "SELECT * FROM UTENTE utenti WHERE utenti.id NOT IN (SELECT DISTINCT Utente_id from INVITO inviti where inviti.Evento_id=:idEvento) AND utenti.id<>:idOrganizzatore"
            , nativeQuery = true)
    List<Utente> findUtentiNonInvitati(@NonNull @Param("idEvento") Long idEvento,
                                       @NonNull @Param("idOrganizzatore") Long idOrganizzatore);

}
