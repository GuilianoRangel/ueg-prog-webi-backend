package br.ueg.prog.webi.adminmodule.mapper;


import br.ueg.prog.webi.adminmodule.dto.model.GrupoDTO;
import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { StatusAtivoInativoMapper.class, SimNaoMapper.class, FuncionalidadeMapper.class})
public interface GrupoMapper {
    /**
     * Converte a entidade {@link Grupo} em DTO {@link GrupoDTO}
     *
     * @param grupo
     * @return
     */

    public GrupoDTO toDTO(Grupo grupo);

    /**
     * Converte o DTO {@link GrupoDTO} para entidade {@link Grupo}
     *
     * @param grupoDTO
     * @return
     */

    public Grupo toEntity(GrupoDTO grupoDTO);
}
