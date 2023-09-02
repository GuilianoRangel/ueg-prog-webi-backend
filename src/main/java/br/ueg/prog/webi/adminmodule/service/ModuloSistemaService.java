/*
 * ModuloService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.service;

import br.ueg.prog.webi.adminmodule.dto.model.ModuloSistemaDTO;
import br.ueg.prog.webi.adminmodule.exception.ModuleAdminMessageCode;
import br.ueg.prog.webi.adminmodule.model.ModuloSistema;
import br.ueg.prog.webi.adminmodule.repository.ModuloSistemaRepository;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Classe de négocio referente a entidade {@link ModuloSistema}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ModuloSistemaService {

	@Autowired
	private ModuloSistemaRepository moduloRepository;

	/**
	 * Retorna uma lista de {@link ModuloSistemaDTO} ativos.
	 *
	 * @return
	 */
	public List<ModuloSistema> getAtivos() {
		List<ModuloSistema> modulos = moduloRepository.getAtivos();

		if (CollectionUtil.isEmpty(modulos)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return modulos;
	}

	/**
	 * Retorna uma lista de {@link ModuloSistemaDTO} cadastrados.
	 *
	 * @return
	 */
	public List<ModuloSistema> getTodos() {
		List<ModuloSistema> modulos = moduloRepository.findAll();

		if (CollectionUtil.isEmpty(modulos)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return modulos;
	}
}
