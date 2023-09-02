package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Responsabilidade;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import br.ueg.prog.webi.faculdade.repository.ResponsabilidadeRepository;
import br.ueg.prog.webi.faculdade.repository.TipoRepository;
import br.ueg.prog.webi.faculdade.service.ResponsabilidadeService;
import br.ueg.prog.webi.faculdade.service.TipoService;
import org.springframework.stereotype.Service;

@Service
public class ResponsabilidadeServiceImpl
        extends BaseCrudService<Responsabilidade, PkResponsabilidade, ResponsabilidadeRepository>
        implements ResponsabilidadeService {

    @Override
    protected void prepararParaIncluir(Responsabilidade entidade) {

    }

    @Override
    protected void validarDados(Responsabilidade entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Responsabilidade entidade) {

    }
}
