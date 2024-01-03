package it.unimib.wegather.frontend.lista.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoModel {

    private Long id;
    private String tipo;
    private String nome;
    private String descrizione;
    private Integer partecipanti;
    private OrganizzatoreModel organizzatore;
    private List<LocalDate> proposte;

}
