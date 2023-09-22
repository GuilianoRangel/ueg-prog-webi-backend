package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.model.IEntidade;
import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.api.util.Reflexao;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import br.ueg.prog.webi.faculdade.repository.FuncionarioRepository;
import br.ueg.prog.webi.faculdade.repository.PessoaRepository;
import br.ueg.prog.webi.faculdade.service.FuncionarioService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FuncionarioServiceImpl  extends BaseCrudService<Funcionario, Long, FuncionarioRepository>
        implements FuncionarioService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    protected void prepararParaIncluir(Funcionario funcionario) {
        tratarPessoa(funcionario);
        funcionario.setNew();
    }

    @Override
    public Funcionario alterar(Funcionario entidade, Long id) {
        tratarPessoa(entidade);
        return super.alterar(entidade, id);
    }

    private void tratarPessoa(Funcionario funcionario) {
        Pessoa pessoaOld = funcionario.getPessoa();
        Map<String, IEntidade<?>> entidadeMap = Reflexao.setForeignEntity(funcionario, this.context);
        // o metodo setForeighEntity retorna uma lista com as entidades
        // que foram recuperadas para o atributo extrangeiro
        // caso não encontre retorna null
        if(Objects.isNull(entidadeMap.get("pessoa"))){
            //Salvar uma nova pessoa
            Pessoa newPessoa = funcionario.getPessoa();
            newPessoa.setNew();;
            Pessoa pessoa = pessoaRepository.save(newPessoa);
            funcionario.setPessoa(pessoa);
        }else{
            //Alterar os daods da pessoa retornado
            funcionario.getPessoa().setNome(pessoaOld.getNome());
            funcionario.getPessoa().setTelefone(pessoaOld.getTelefone());
            funcionario.getPessoa().setEmail(pessoaOld.getEmail());
        }
    }

    @Override
    protected void validarDados(Funcionario entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Funcionario entidade) {

    }
}
