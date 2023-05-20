package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Livro;
import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.faculdade.repository.LivroRepository;
import br.ueg.prog.webi.faculdade.service.LivroService;
import org.springframework.stereotype.Service;

@Service
public class LivroServiceImpl
        extends BaseCrudService<Livro, Long, LivroRepository>
        implements LivroService {
    @Override
    protected void prepararParaIncluir(Livro entidade) {
        entidade.setStatus(StatusAtivoInativo.ATIVO);
    }

    @Override
    protected void validarDados(Livro entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Livro entidade) {

    }
}
