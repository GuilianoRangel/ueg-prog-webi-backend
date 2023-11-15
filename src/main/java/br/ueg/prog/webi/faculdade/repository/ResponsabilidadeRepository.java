package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Responsabilidade;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsabilidadeRepository
        extends JpaRepository<Responsabilidade, PkResponsabilidade>, JpaSpecificationExecutor<Responsabilidade> {
    @Query("select r " +
            "From Responsabilidade r " +
            "inner join fetch r.funcionario f " +
            "inner join fetch r.local l " +
            "inner join fetch f.cargo c " +
            " where r.sequencia = :#{#pk.sequencia} " +
            " and r.local.numeroSala = :#{#pk.local}")
    Optional<Responsabilidade> findById(PkResponsabilidade pk);
}
