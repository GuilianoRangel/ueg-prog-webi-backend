package br.ueg.prog.webi.faculdade.service;

import br.ueg.prog.webi.api.service.CrudService;
import br.ueg.prog.webi.faculdade.model.Tipo;

public interface TipoService
        extends CrudService<Tipo, Long> {
    Tipo desativar(Long id);

}
