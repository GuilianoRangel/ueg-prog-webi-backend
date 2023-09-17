package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.exception.SistemaMessageCode;
import br.ueg.prog.webi.faculdade.model.Chave;
import br.ueg.prog.webi.faculdade.model.Emprestimo;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.repository.EmprestimoRepository;
import br.ueg.prog.webi.faculdade.repository.LocalRepository;
import br.ueg.prog.webi.faculdade.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocalServiceImpl extends BaseCrudService<Local, Long, LocalRepository>
        implements LocalService {

    @Autowired
    protected EmprestimoRepository emprestimoRepository;
    @Override
    protected void prepararParaIncluir(Local local) {
        tratarChaves(local);
        validarDuplicado(local);
    }

    private void validarDuplicado(Local local) {
        Local local1 = this.recuperarEntidade(local.getId());
        if(Objects.nonNull(local1)){
            throw new BusinessException(SistemaMessageCode.MSG_LOCAL_JA_EXISTE);
        }
    }

    @Override
    public Local alterar(Local local, Long id) {
        tratarChaves(local);
        return super.alterar(local, id);
    }

    private void tratarChaves(Local local) {
        if(Objects.nonNull(local)){
            local.getChaves().stream()
                    .forEach(chave -> {
                        chave.setLocal(local);
                        chave.setQrCode("L"
                                .concat(local.getNumeroSala().toString())
                                        .concat("C")
                                .concat(String.valueOf(chave.getNumero()))
                        );
                    });
        }
    }

    @Override
    protected void validarDados(Local entidade) {
        if(Objects.nonNull(entidade.getId())){
            Local local = this.recuperarEntidade(entidade.getId());
            Set<Chave> chaveSalvar = Objects.nonNull(entidade.getChaves()) ? entidade.getChaves() : new HashSet<Chave>();
            validarSalvarChaveDuplicada(local, chaveSalvar);
            validarRemocaoChave(local, chaveSalvar);
        }
    }

    private void validarSalvarChaveDuplicada(Local local, Set<Chave> chaveSalvar) {
        if(Objects.nonNull(chaveSalvar) && !chaveSalvar.isEmpty()){
            Map<Long, List<Chave>> collect = chaveSalvar.stream().collect(Collectors.groupingBy(chave -> chave.getNumero()));
            if(collect.values().stream().filter(chaves -> chaves.size()>1).count() > 0){
                throw new BusinessException(SistemaMessageCode.MSG_CHAVE_JA_EXISTE);
            }
        }
    }

    private void validarRemocaoChave(Local local, Set<Chave> chaveSalvar) {
        if(Objects.isNull(local)){
            return;
        }
        local.getChaves().stream()
                .filter(chave -> !chaveSalvar.contains(chave))
                .forEach(chave -> {
                    Optional<List<Emprestimo>> emprestimos = emprestimoRepository
                            .findByChave_NumeroAndChave_Local_Id(chave.getNumero(), chave.getLocal().getId());
                    if(emprestimos.isPresent() && emprestimos.get().size() > 0){
                        throw new BusinessException(SistemaMessageCode.MSG_CHAVE_JA_UTILIZADA);
                    }
                });
    }

    @Override
    protected void validarCamposObrigatorios(Local entidade) {

    }
}
