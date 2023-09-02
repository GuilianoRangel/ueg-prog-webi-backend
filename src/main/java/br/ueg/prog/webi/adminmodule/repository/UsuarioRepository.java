/*
 * UsuarioRepository.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.repository;

import br.ueg.prog.webi.adminmodule.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface UsuarioRepository extends UsuarioRepositoryCustom, JpaRepository<Usuario, Long> {
	/**
	 * Retorna a instância do {@link Usuario} conforme o 'login' informado.
	 * 
	 * @param login
	 * @return
	 */
	public Usuario findByLogin(final String login);

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'login' informado
	 * e que não tenha o 'id' informado.
	 * 
	 * @param login
	 * @param id
	 * @return
	 */
	@Query(" SELECT usuario FROM Usuario usuario "
			+ " WHERE usuario.login = :login AND (:id IS NULL OR usuario.id != :id)")
	public Usuario findByLoginAndNotId(@Param("login") final String login, @Param("id") final Long id);

	/**
	 * Retorna uma instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	@Query(" SELECT usuario FROM Usuario usuario "
			+ " LEFT JOIN FETCH usuario.grupos usuarioGrupo "
			+ " LEFT JOIN FETCH usuarioGrupo.grupo grupo "
			+ " LEFT JOIN FETCH usuario.telefones telefone "
			+ " WHERE usuario.id = :id ")
	public Optional<Usuario> findByIdFetch(@Param("id") final Long id);


	/**
	 * Retorna o total de Usuários encontrados na base de dados conforme o Login
	 * informado.
	 *
	 * @param login login do usuário
	 * @return
	 */
	public Long countByLogin(final String login);

}
