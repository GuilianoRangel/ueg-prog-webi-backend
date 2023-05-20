package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Aluno2Repository extends JpaRepository<Aluno, Long>{

}
