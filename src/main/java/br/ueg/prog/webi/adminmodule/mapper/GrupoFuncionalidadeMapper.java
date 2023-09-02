package br.ueg.prog.webi.adminmodule.mapper;


import br.ueg.prog.webi.adminmodule.dto.model.GrupoFuncionalidadeDTO;
import br.ueg.prog.webi.adminmodule.model.GrupoFuncionalidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link GrupoFuncionalidade}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class, FuncionalidadeMapper.class, GrupoMapper.class})
public interface GrupoFuncionalidadeMapper {
    /**
     * Converte a entidade {@link GrupoFuncionalidade} em DTO {@link GrupoFuncionalidadeDTO}
     *
     * @param grupoFuncionalidade
     * @return
     */
    @Mapping(source = "grupo.id", target = "idGrupo")
    public GrupoFuncionalidadeDTO toDTO(GrupoFuncionalidade grupoFuncionalidade);

    /**
     * Converte o DTO {@link GrupoFuncionalidadeDTO} para entidade {@link GrupoFuncionalidade}
     *
     * @param grupoFuncionalidadeDTO
     * @return
     */
    @Mapping(source = "idGrupo", target = "grupo.id")
    public GrupoFuncionalidade toEntity(GrupoFuncionalidadeDTO grupoFuncionalidadeDTO);
}
