package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Chave;
import br.ueg.prog.webi.faculdade.model.Responsabilidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChaveRepository
        extends JpaRepository<Chave, PkChave>{
    @Query("select c " +
            "From Chave c " +
            "inner join fetch c.local l " +
            " where c.numero = :#{#pk.numero} " +
            " and c.local.numeroSala = :#{#pk.local}")
    Optional<Chave> findById(PkChave pk);
}
