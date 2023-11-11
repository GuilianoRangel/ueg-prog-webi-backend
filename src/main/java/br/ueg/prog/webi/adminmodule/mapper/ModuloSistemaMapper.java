package br.ueg.prog.webi.adminmodule.mapper;


import br.ueg.prog.webi.adminmodule.dto.model.ModuloSistemaDTO;
import br.ueg.prog.webi.adminmodule.model.ModuloSistema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link ModuloSistema}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class, FuncionalidadeMapper.class})
public interface ModuloSistemaMapper {
    /**
     * Converte a entidade {@link ModuloSistema} em DTO {@link ModuloSistemaDTO}
     *
     * @param moduloSistema -
     * @return -
     */

    @Mapping(source = "status.id", target = "idStatus")
    @Mapping(source = "status.description", target = "descricaoStatus")
    ModuloSistemaDTO toDTO(ModuloSistema moduloSistema);

    /**
     * Converte o DTO {@link ModuloSistemaDTO} para entidade {@link ModuloSistema}
     *
     * @param moduloSistemaDTO -
     * @return -
     */

    @Mapping(target = "status", expression = "java( StatusAtivoInativo.getById( moduloSistemaDTO.getIdStatus() ) )")

    ModuloSistema toEntity(ModuloSistemaDTO moduloSistemaDTO);
}
