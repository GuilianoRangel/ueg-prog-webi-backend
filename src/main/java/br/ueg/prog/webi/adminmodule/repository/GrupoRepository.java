package br.ueg.prog.webi.adminmodule.repository;

import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.model.Usuario;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>, GrupoRepositoryCustom {

    @Query("select ug from Grupo ug" +
            " where " +
            " ug.nome=:nome " +
            " and ug.status=:status")
    List<Grupo> findByFiltro(String nome, StatusAtivoInativo status);



    /**
     * Retorna os roles do sistema conforme o 'idUsuario' e o 'mnemonico'
     * informados.
     *
     * @param idUsuario
     * @return
     */
    @Query("SELECT CONCAT('ROLE_', moduloSistema.mnemonico, '_', funcionalidade.mnemonico) FROM UsuarioGrupo usuarioGrupo "
            + " INNER JOIN usuarioGrupo.grupo grupo INNER JOIN usuarioGrupo.usuario usuario"
            + " INNER JOIN grupo.grupoFuncionalidades grupoFuncionalidade"
            + " INNER JOIN grupoFuncionalidade.funcionalidade funcionalidade "
            + " INNER JOIN funcionalidade.moduloSistema moduloSistema"
            + " WHERE grupo.status='A' AND funcionalidade.status='A' AND moduloSistema.status='A' "
            + " AND usuario.id = :idUsuario")
    public List<String> findRolesByUsuario(@Param("idUsuario") final Long idUsuario);

    /**
     * Retorna o número de {@link Grupo} pelo 'nome' e 'Sistema', desconsiderando o
     * 'Grupo' com o 'id' informado.
     *
     * @param nome
     * @param idGrupo
     * @return
     */
    @Query("SELECT COUNT(grupo) FROM Grupo grupo " +
            " WHERE lower(grupo.nome) LIKE lower(:nome) " +
            " AND (:idGrupo IS NULL OR grupo.id != :idGrupo)")
    public Long countByNomeAndGrupoAndNotId(@Param("nome") final String nome,
                                            @Param("idGrupo") final Long idGrupo);

    /**
     * Retorna o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @Query("SELECT grupo FROM Grupo grupo "
            + "	LEFT JOIN FETCH grupo.grupoFuncionalidades grupoFuncionalidades"
            + "	LEFT JOIN FETCH grupoFuncionalidades.funcionalidade funcionalidade"
            + "	LEFT JOIN FETCH funcionalidade.moduloSistema WHERE grupo.id = :id")
    public Grupo findByIdFetch(@Param("id") final Long id);

    /**
	 * Retorna uma lista de {@link Grupo} ativos .
	 *
	 * @return
	 */
	@Query(" SELECT DISTINCT grupo FROM Grupo grupo "
			+ " WHERE grupo.status = 'A' "
			+ " ORDER BY grupo.nome ASC ")
	public List<Grupo> getAtivos();
}
