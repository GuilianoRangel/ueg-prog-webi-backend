package br.ueg.prog.webi.faculdade.service;

import br.ueg.prog.webi.api.service.CrudService;
import br.ueg.prog.webi.faculdade.model.Aluno;

import java.util.List;

public interface AlunoService extends CrudService<Aluno, Long> {

    List<Aluno> localizar(Aluno aluno);
    Aluno cancelarMatricula(Long id);
}
