package it.unimib.wegather.backend.repository;

import it.unimib.wegather.backend.entity.Evento;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByOrganizzatoreId(@NonNull Long id);

    List<Evento> findByInvitatiId(@NonNull Long id);

}
