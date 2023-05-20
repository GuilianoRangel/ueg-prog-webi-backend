package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.repository.Aluno2Repository;
import br.ueg.prog.webi.faculdade.service.Aluno2Service;
import org.springframework.stereotype.Service;

@Service
public class Aluno2ServiceImpl
        extends BaseCrudService<Aluno, Long, Aluno2Repository>
        implements Aluno2Service {
    @Override
    protected void prepararParaIncluir(Aluno entidade) {
        entidade.setStatusMatricula("Em aprovação");
    }

    @Override
    protected void validarDados(Aluno entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Aluno entidade) {

    }
}
