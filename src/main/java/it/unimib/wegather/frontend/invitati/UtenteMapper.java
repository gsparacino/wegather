package it.unimib.wegather.frontend.invitati;

import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.invitati.model.UtenteModel;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
class UtenteMapper {

    static UtenteModel map(@NonNull Utente utente) {
        UtenteModel model = new UtenteModel();
        model.setId(utente.getId());
        model.setUsername(utente.getUsername());
        return model;
    }

    static List<UtenteModel> map(@NonNull List<Utente> utenti) {
        return utenti.stream()
                .map(UtenteMapper::map)
                .collect(Collectors.toList());
    }
}
