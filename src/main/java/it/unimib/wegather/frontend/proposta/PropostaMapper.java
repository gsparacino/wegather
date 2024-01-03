package it.unimib.wegather.frontend.proposta;

import it.unimib.wegather.backend.entity.Proposta;
import it.unimib.wegather.frontend.proposta.model.PropostaModel;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
class PropostaMapper {

    static PropostaModel map(@NonNull Proposta proposta) {
        PropostaModel model = new PropostaModel();
        model.setId(proposta.getId());
        model.setData(proposta.getData());
        return model;
    }

    static List<PropostaModel> map(@NonNull List<Proposta> proposte) {
        return proposte.stream()
                .map(PropostaMapper::map)
                .collect(Collectors.toList());
    }
}
