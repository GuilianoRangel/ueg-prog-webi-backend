package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import br.ueg.prog.webi.faculdade.repository.PessoaRepository;
import br.ueg.prog.webi.faculdade.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl extends BaseCrudService<Pessoa, Long, PessoaRepository>
        implements PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    protected void prepararParaIncluir(Pessoa pessoa) {
        pessoa.setNew();
    }

    @Override
    protected void validarDados(Pessoa entidade) {
    }

    @Override
    protected void validarCamposObrigatorios(Pessoa entidade) {

    }
}
