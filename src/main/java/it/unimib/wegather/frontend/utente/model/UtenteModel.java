package it.unimib.wegather.frontend.utente.model;

import lombok.Data;

@Data
public class UtenteModel {

    private Long id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;

}
