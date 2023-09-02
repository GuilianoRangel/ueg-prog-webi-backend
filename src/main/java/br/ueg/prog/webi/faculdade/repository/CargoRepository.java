package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Cargo;
import br.ueg.prog.webi.faculdade.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository
        extends JpaRepository<Cargo, Long>{

}
