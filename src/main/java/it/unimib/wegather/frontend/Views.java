package it.unimib.wegather.frontend;

import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Utente;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration di tutte le View previste dall'applicazione.
 */
@RequiredArgsConstructor
@Getter
public enum Views {

    /**
     * Pagina di login iniziale
     */
    LOGIN("login"),


    /**
     * Pagina di registrazione
     */
    REGISTER("register"),

    /**
     * Pagina con tutti le istanze di {@link Evento} associate ad un {@link Utente}
     */
    LISTA("lista"),

    /**
     * Pagina per la modifica o la creazione di un {@link Evento}
     */
    EVENTO("evento"),

    /**
     * Pagina di conferma per la cancellazione di un {@link Evento}
     */
    EVENTO_DELETE("evento_delete"),

    /**
     * Pagina di modifica delle informazioni personali dell'{@link Utente}
     */
    INFO("info"),

    UTENTE("utente"),

    PARTECIPANTI("partecipanti"),
    PROPOSTE("proposte"),
    ;

    private final String id;

}
