package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.repository.LocalRepository;
import br.ueg.prog.webi.faculdade.service.LocalService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LocalServiceImpl extends BaseCrudService<Local, Long, LocalRepository>
        implements LocalService {
    @Override
    protected void prepararParaIncluir(Local local) {
        tratarChaves(local);
    }

    @Override
    public Local alterar(Local local, Long id) {
        tratarChaves(local);
        return super.alterar(local, id);
    }

    private void tratarChaves(Local local) {
        if(Objects.nonNull(local)){
            local.getChaves().stream()
                    .forEach(chave -> {chave.setLocal(local);});
        }
    }

    @Override
    protected void validarDados(Local entidade) {
        //TODO verifcar quais validações
    }

    @Override
    protected void validarCamposObrigatorios(Local entidade) {

    }
}
