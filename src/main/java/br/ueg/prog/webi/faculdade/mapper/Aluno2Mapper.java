package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.AlunoDTO;
import br.ueg.prog.webi.faculdade.model.Aluno;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Aluno2Mapper extends BaseMapper<Aluno, AlunoDTO> {
}
