package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
