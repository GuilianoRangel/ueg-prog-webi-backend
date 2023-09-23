package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.model.Responsabilidade;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import br.ueg.prog.webi.faculdade.repository.FuncionarioRepository;
import br.ueg.prog.webi.faculdade.repository.LocalRepository;
import br.ueg.prog.webi.faculdade.repository.ResponsabilidadeRepository;
import br.ueg.prog.webi.faculdade.service.ResponsabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ResponsabilidadeServiceImpl
        extends BaseCrudService<Responsabilidade, PkResponsabilidade, ResponsabilidadeRepository>
        implements ResponsabilidadeService {
    
    @Autowired
    FuncionarioRepository funcionarioRepository;
    
    @Autowired
    LocalRepository localRepository;

    @Override
    protected void prepararParaIncluir(Responsabilidade resp) {
    }

    @Override
    protected void validarDados(Responsabilidade entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Responsabilidade entidade) {

    }
}
