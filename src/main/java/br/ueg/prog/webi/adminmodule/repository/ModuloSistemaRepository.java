/*
 * ModuloRepository.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.repository;

import br.ueg.prog.webi.adminmodule.model.ModuloSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link br.ueg.prog.webi.adminmodule.model.ModuloSistema}.
 *
 * @author UEG
 */
@Repository
public interface ModuloSistemaRepository extends JpaRepository<ModuloSistema, Long> {

	/**
	 * Retorna uma lista de {@link ModuloSistema} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@Query(" SELECT DISTINCT modulo FROM ModuloSistema modulo "
			+ " INNER JOIN FETCH modulo.funcionalidades funcionalidades"
			+ " WHERE modulo.status = 'A'"
			+ " ORDER BY modulo.nome ASC, funcionalidades.nome ASC ")
	public List<ModuloSistema> getAtivos();

}
