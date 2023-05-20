package br.ueg.prog.webi.faculdade.repository;

import br.ueg.prog.webi.faculdade.model.Aluno;

import java.util.List;

public interface AlunoRepositoryCustom {
    List<Aluno> localizarPorAluno(Aluno aluno);
}
