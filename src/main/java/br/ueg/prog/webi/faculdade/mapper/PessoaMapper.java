package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.PessoaDTO;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PessoaMapper
        extends BaseMapper<Pessoa, PessoaDTO> {
}
