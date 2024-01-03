package it.unimib.wegather.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Event
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descrizione;

    private String indirizzo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposta> proposte = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Evento eventoPrecedente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "INVITO", inverseJoinColumns = @JoinColumn(name = "Utente_id"), joinColumns = @JoinColumn(name = "Evento_id"))
    private List<Utente> invitati = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Utente organizzatore;


    public void removeProposta(Proposta proposta){
        proposte.remove(proposta);
    }
}


