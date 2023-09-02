package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Discente;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.pks.PkDiscente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscenteRepository
        extends JpaRepository<Discente, Long>{
    @Query("select d " +
            "From Discente d " +
            "inner join fetch d.pessoa " +
            " where d.cpf = :cpf")
    Optional<Discente> findByCPF(Long cpf);
}
