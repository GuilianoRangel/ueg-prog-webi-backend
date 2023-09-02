package br.ueg.prog.webi.adminmodule.repository;

import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.model.GrupoFuncionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface GrupoFuncionalidadeRepository extends JpaRepository<GrupoFuncionalidade, Long> {
    Set<GrupoFuncionalidade> findByGrupo(Grupo grupo);

    /**
     * Retorna o {@link GrupoFuncionalidade} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @Query("SELECT grupoFuncionalidade FROM GrupoFuncionalidade grupoFuncionalidade "
            + " INNER JOIN FETCH grupoFuncionalidade.grupo grupo"
            + "	INNER JOIN FETCH grupoFuncionalidade.funcionalidade"
            + "	WHERE grupoFuncionalidade.id = :id")
    public GrupoFuncionalidade findByIdFetch(@Param("id") final Long id);

}
