package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalRepository
        extends JpaRepository<Local, Long>, JpaSpecificationExecutor<Local> {
    @Query("select l " +
            "From Local l " +
            "left join fetch l.chaves " +
            " where l.numeroSala = :pk " )
    Optional<Local> findById(Long pk);
}
