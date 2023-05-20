package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>, AlunoRepositoryCustom {

    Optional<Aluno> findAlunoByeMail(String eMail);

    @Query(value = "select count(a) from Aluno a where a.eMail=:paramMail")
    Integer countUtilizacaoEMail(String paramMail);
}
