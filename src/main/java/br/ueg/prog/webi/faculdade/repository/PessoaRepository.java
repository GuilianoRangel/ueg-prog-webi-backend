package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import br.ueg.prog.webi.faculdade.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository
        extends JpaRepository<Pessoa, Long>{

}
