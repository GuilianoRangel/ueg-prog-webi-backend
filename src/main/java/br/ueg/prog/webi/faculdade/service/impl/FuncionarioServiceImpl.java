package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.model.IEntidade;
import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.api.util.Reflexao;
import br.ueg.prog.webi.faculdade.mapper.PessoaMapper;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import br.ueg.prog.webi.faculdade.repository.FuncionarioRepository;
import br.ueg.prog.webi.faculdade.repository.PessoaRepository;
import br.ueg.prog.webi.faculdade.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class FuncionarioServiceImpl  extends BaseCrudService<Funcionario, Long, FuncionarioRepository>
        implements FuncionarioService {

    @Override
    protected void prepararParaIncluir(Funcionario funcionario) {
    }


    @Override
    protected void validarDados(Funcionario entidade) {
    }

    @Override
    protected void validarCamposObrigatorios(Funcionario entidade) {

    }
}
