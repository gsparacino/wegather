package it.unimib.wegather.frontend.evento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoInLocaleModel extends EventoModel {

    private Double costo;
    private String locale;

}
