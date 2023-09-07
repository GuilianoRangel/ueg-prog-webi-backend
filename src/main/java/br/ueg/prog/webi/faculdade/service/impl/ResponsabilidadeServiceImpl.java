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
        tratarLocal(resp);
        tratarFuncionario(resp);
    }

    private void tratarFuncionario(Responsabilidade resp) {
        if(Objects.nonNull(resp.getFuncionario())){
           Funcionario funcOpt = funcionarioRepository.getReferenceById(resp.getFuncionario().getId());
           if(Objects.nonNull(funcOpt)){
               resp.setFuncionario(funcOpt);
           }
        }
    }

    private void tratarLocal(Responsabilidade resp) {
       if(Objects.nonNull(resp.getLocal())){
           Local local = localRepository.getReferenceById(resp.getLocal().getId());
           if(Objects.nonNull(local)){
               resp.setLocal(local);
           }
       }
    }

    @Override
    protected void validarDados(Responsabilidade entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Responsabilidade entidade) {

    }

    @Override
    public Responsabilidade alterar(Responsabilidade resp, PkResponsabilidade id) {
        tratarLocal(resp);
        tratarFuncionario(resp);
        return super.alterar(resp, id);
    }
}
