package it.unimib.wegather.service;


import it.unimib.wegather.backend.entity.Evento;
import it.unimib.wegather.backend.entity.Proposta;
import it.unimib.wegather.backend.repository.EventoRepository;
import it.unimib.wegather.backend.repository.PropostaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final EventoRepository eventoRepository;
    private final PropostaRepository propostaRepository;

    public Evento setProposteEvento(@NonNull List<Proposta> proposte,
                                    @NonNull Evento evento){
        evento.setProposte(proposte);
        return eventoRepository.saveAndFlush(evento);
    }

    public List<Proposta> getProposteEvento(@NonNull Evento evento){
        return propostaRepository.findByEventoId(evento.getId());
    }

    public Evento deleteProposta(Long idEvento, Long idProposta){
        Evento evento =  eventoRepository.findById(idEvento).orElseThrow(() -> new IllegalArgumentException("L'evento con id " + idEvento + " non è presente a sistema"));
        Proposta proposta = propostaRepository.getById(idProposta);
        evento.removeProposta(proposta);
        eventoRepository.saveAndFlush(evento);
        return evento;
    }

}
