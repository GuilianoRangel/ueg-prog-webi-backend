package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.model.pks.PkFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long>{

    @Query("select f " +
            "From Funcionario f " +
            "inner join fetch f.pessoa p " +
            "inner join fetch f.cargo " +
            " where f.cpf = :cpf")
    Optional<Funcionario> findByCPF(Long cpf);
}
