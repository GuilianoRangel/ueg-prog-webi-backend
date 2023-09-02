package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Emprestimo;
import br.ueg.prog.webi.faculdade.model.PessoaPermissao;
import br.ueg.prog.webi.faculdade.model.pks.PkEmprestimo;
import br.ueg.prog.webi.faculdade.model.pks.PkPessoaPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, PkEmprestimo>{
    @Query("select e " +
            "From Emprestimo e " +
            "inner join fetch e.pessoaPermissao pp " +
            "inner join fetch pp.responsabilidade  r "+
            "inner join fetch r.funcionario f " +
            "inner join fetch r.local l " +
            "inner join fetch f.cargo c " +
            "inner join fetch pp.pessoa p " +
            "inner join fetch e.chave ch " +
            " where e.chave.numero = :#{#pk.chave.numero} " +
            " and e.chave.local = :#{#pk.chave.local} " +
            " and pp.sequencia = :#{#pk.pessoaPermissao.sequencia} " +
            //" and r.local.numeroSala = :#{#pk.pessoaPermissa.responsabilidade.local} " +
            " and r.sequencia = :#{#pk.pessoaPermissa.responsabilidade.sequencia} " +
            "")
    Optional<Emprestimo> findById(PkEmprestimo pk);
}
