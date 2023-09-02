/*
 * TelefoneUsuarioMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.mapper;

import br.ueg.prog.webi.adminmodule.dto.model.TelefoneUsuarioDTO;
import br.ueg.prog.webi.adminmodule.model.TelefoneUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link TelefoneUsuario}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface TelefoneUsuarioMapper {

	/**
	 * Converte a entidade {@link TelefoneUsuario} em DTO {@link TelefoneUsuarioDTO}.
	 * 
	 * @param telefoneUsuario
	 * @return
	 */
	@Mapping(source = "usuario.id", target = "idUsuario")
	@Mapping(source = "tipo.id", target = "idTipo")
    @Mapping(source = "tipo.descricao", target = "descricaoTipo")
	public TelefoneUsuarioDTO toDTO(TelefoneUsuario telefoneUsuario);

	/**
	 * Converte o DTO {@link TelefoneUsuarioDTO} para entidade {@link TelefoneUsuario}.
	 * 
	 * @param telefoneUsuarioDTO
	 * @return
	 */
	@Mapping(source = "idUsuario", target = "usuario.id")
	@Mapping(target = "tipo", expression = "java( TipoTelefone.getById( telefoneUsuarioDTO.getIdTipo() ) )")
	public TelefoneUsuario toEntity(TelefoneUsuarioDTO telefoneUsuarioDTO);
}
