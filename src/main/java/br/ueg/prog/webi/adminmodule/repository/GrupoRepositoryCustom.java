/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.repository;


import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroGrupoDTO;
import br.ueg.prog.webi.adminmodule.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface GrupoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link FiltroGrupoDTO} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroGrupoDTO
	 * @return
	 */
	public List<Grupo> findAllByFiltro(FiltroGrupoDTO filtroGrupoDTO);

	/**
	 * Retorna uma lista de {@link FiltroGrupoDTO} conforme o 'id' do Sistema informado.
	 * 
	 * @param idSistema
	 * @return
	 */
	public List<Grupo> getGruposAtivosByIdSistema(final Long idSistema);

}
