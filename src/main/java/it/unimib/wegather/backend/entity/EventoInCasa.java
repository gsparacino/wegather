package it.unimib.wegather.backend.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Event at home. Inherits from {@link Evento}
 */
@Entity
@Data
public class EventoInCasa extends Evento {

    private String citofono;

}
