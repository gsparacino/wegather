package it.unimib.wegather.frontend.evento.model;

import lombok.Data;


/**
 * Generic Event model
 */
@Data
public class EventoModel {

    private Long id;
    private String nome;
    private String descrizione;
    private EventoModel eventoPrecedente;
    private String indirizzo;

}
