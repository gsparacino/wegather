package it.unimib.wegather.frontend.utente;

import it.unimib.wegather.backend.entity.Utente;
import it.unimib.wegather.frontend.utente.model.UtenteModel;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
class UtenteMapper {

    static UtenteModel map(@NonNull Utente utente) {
        UtenteModel model = new UtenteModel();
        model.setId(utente.getId());
        model.setUsername(utente.getUsername());
        model.setNome(utente.getNome());
        model.setCognome(utente.getCognome());
        model.setEmail(utente.getEmail());
        model.setTelefono(utente.getTelefono());
        return model;
    }

}
