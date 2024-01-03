package it.unimib.wegather.frontend.evento.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TipoEventoModel {

    IN_CASA("casa"),
    IN_LOCALE("locale"),
    ;

    @Getter
    private final String id;

    public static TipoEventoModel formValue(@NonNull String value) {
        for (TipoEventoModel tipo : TipoEventoModel.values()) {
            if (tipo.getId().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("La tipologia di evento '" + value + "' non è supportata");
    }
}
