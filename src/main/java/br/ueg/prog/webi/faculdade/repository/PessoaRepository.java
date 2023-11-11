package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository
        extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

}
