package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository
        extends JpaRepository<Local, Long>{

}
