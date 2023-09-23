package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.ResponsabilidadeDTO;
import br.ueg.prog.webi.faculdade.dto.TipoDTO;
import br.ueg.prog.webi.faculdade.model.Responsabilidade;
import br.ueg.prog.webi.faculdade.model.Tipo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses =
        {FuncionarioMapper.class})
public interface ResponsabilidadeMapper
        extends BaseMapper<Responsabilidade, ResponsabilidadeDTO> {
}
