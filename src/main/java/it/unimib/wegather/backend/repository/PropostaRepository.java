package it.unimib.wegather.backend.repository;


import it.unimib.wegather.backend.entity.Proposta;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findByEventoId(@NonNull Long id);

    @Override
    void deleteById(Long id);
}
