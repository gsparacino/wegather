package it.unimib.wegather.frontend.lista.model;

import lombok.Data;

import java.util.List;

import static java.util.Collections.emptyList;

@Data
public class EventiModel {

    private List<EventoModel> organizzatore = emptyList();
    private List<EventoModel> partecipante = emptyList();


}
