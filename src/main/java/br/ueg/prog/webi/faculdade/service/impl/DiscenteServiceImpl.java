package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Discente;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.repository.DiscenteRepository;
import br.ueg.prog.webi.faculdade.repository.PessoaRepository;
import br.ueg.prog.webi.faculdade.service.DiscenteService;
import br.ueg.prog.webi.faculdade.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscenteServiceImpl extends BaseCrudService<Discente, Long, DiscenteRepository>
        implements DiscenteService {

    @Override
    protected void prepararParaIncluir(Discente discente) {
    }


    @Override
    protected void validarDados(Discente entidade) {
    }

    @Override
    protected void validarCamposObrigatorios(Discente entidade) {

    }
}
