package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.PessoaPermissao;
import br.ueg.prog.webi.faculdade.model.pks.PkPessoaPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaPermissaoRepository
        extends JpaRepository<PessoaPermissao, PkPessoaPermissao>, JpaSpecificationExecutor<PessoaPermissao> {
    @Query("select pp " +
            "From PessoaPermissao pp " +
            "inner join fetch pp.responsabilidade  r "+
            "inner join fetch r.funcionario f " +
            "inner join fetch r.local l " +
            "inner join fetch f.cargo c " +
            "inner join fetch pp.pessoa p " +
            " where pp.sequencia = :#{#pk.sequencia} " +
            " and r.local.numeroSala = :#{#pk.responsabilidade.local} " +
            " and r.sequencia = :#{#pk.responsabilidade.sequencia}")
    Optional<PessoaPermissao> findById(PkPessoaPermissao pk);
}
