package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.faculdade.dto.AlunoDTO;
import br.ueg.prog.webi.faculdade.dto.AlunoDadosAlteravelDTO;
import br.ueg.prog.webi.faculdade.dto.AlunoListaDTO;
import br.ueg.prog.webi.faculdade.model.Aluno;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    /**
     * Converte a Entidade {@link Aluno} em {@link AlunoListaDTO}
     * @param aluno
     * @return
     */
    AlunoListaDTO toDTO(Aluno aluno);

    List<AlunoListaDTO> toDTO(List<Aluno> alunos);

    AlunoDadosAlteravelDTO toAlunoIncluirDTO(Aluno aluno);

    Aluno toModelo(AlunoDadosAlteravelDTO aluno);

    AlunoDTO toAlunoDTO(Aluno aluno);

    Aluno toModelo(AlunoDTO aluno);

}
