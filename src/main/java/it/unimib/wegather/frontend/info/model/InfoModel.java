package it.unimib.wegather.frontend.info.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoModel {

    private String nome;
    private String cognome;
    private String email;
    private String telefono;

}
