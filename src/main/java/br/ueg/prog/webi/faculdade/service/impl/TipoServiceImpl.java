package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.faculdade.repository.TipoRepository;
import br.ueg.prog.webi.faculdade.service.TipoService;
import org.springframework.stereotype.Service;

@Service
public class TipoServiceImpl
        extends BaseCrudService<Tipo, Long, TipoRepository>
        implements TipoService {
    @Override
    protected void prepararParaIncluir(Tipo entidade) {
        entidade.setStatus(StatusAtivoInativo.ATIVO);
    }

    @Override
    protected void validarDados(Tipo entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Tipo entidade) {

    }
    public Tipo desativar(Long id){
        Tipo tipo = this.recuperarEntidadeOuGeraErro(id);
        tipo.setStatus(StatusAtivoInativo.INATIVO);
        tipo = this.repository.save(tipo);
        return tipo;
    }
}
