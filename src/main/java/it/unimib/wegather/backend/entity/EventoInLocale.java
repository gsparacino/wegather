package it.unimib.wegather.backend.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class EventoInLocale extends Evento {

    private Double costo;
    private String locale;

}
