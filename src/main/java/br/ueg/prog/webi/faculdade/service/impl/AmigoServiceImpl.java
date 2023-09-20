package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.api.service.BaseCrudService;
import br.ueg.prog.webi.faculdade.model.Amigo;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.repository.AmigoRepository;
import br.ueg.prog.webi.faculdade.repository.TipoRepository;
import br.ueg.prog.webi.faculdade.service.AmigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AmigoServiceImpl
        extends BaseCrudService<Amigo, Long, AmigoRepository>
        implements AmigoService {
    @Autowired
    private TipoRepository tipoRepository;
    @Override
    protected void prepararParaIncluir(Amigo entidade) {
        tratarTipo(entidade);
    }

    private void tratarTipo(Amigo amigo) {
        if(Objects.isNull(amigo) ||
                Objects.isNull(amigo.getTipo()) ||
                Objects.isNull(amigo.getTipo().getId())
        ) return;
        Optional<Tipo> tipoOptional = tipoRepository.findById(amigo.getTipo().getId());
        tipoOptional.ifPresent(tipo -> amigo.setTipo(tipo));
    }

    @Override
    public Amigo alterar(Amigo entidade, Long id) {
        this.tratarTipo(entidade);
        return super.alterar(entidade, id);
    }

    @Override
    protected void validarDados(Amigo entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Amigo entidade) {

    }

}
