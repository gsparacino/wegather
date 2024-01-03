package it.unimib.wegather.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;

    private String nome;

    private String cognome;

    private String email;

    private String telefono;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "invitati")
    private List<Evento> inviti;

}
