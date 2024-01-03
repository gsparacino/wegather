package it.unimib.wegather.frontend.info;

import it.unimib.wegather.frontend.info.model.InfoModel;
import it.unimib.wegather.backend.entity.Utente;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
class InfoModelMapper {

    static InfoModel map(@NonNull Utente entity) {
        return InfoModel.builder()
                .nome(entity.getNome())
                .cognome(entity.getCognome())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .build();
    }
}
