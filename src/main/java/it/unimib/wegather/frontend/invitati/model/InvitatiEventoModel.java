package it.unimib.wegather.frontend.invitati.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitatiEventoModel {
    private String id;
    private String nome;
    private String cognome;
    private String username;
}
