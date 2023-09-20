package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Amigo;
import br.ueg.prog.webi.faculdade.model.Chave;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmigoRepository
        extends JpaRepository<Amigo, Long>{
    @Query("select a " +
            "From Amigo a " +
            "inner join fetch a.tipo t " +
            " where a.id = :id ")
    Optional<Amigo> findById(Long id);
}
