package it.unimib.wegather.frontend.evento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represent an event in house, inherits from generic Event
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoInCasaModel extends EventoModel {

    private String citofono;

}
