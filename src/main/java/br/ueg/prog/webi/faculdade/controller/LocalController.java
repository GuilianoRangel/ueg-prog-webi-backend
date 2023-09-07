package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudEntityIdHashController;
import br.ueg.prog.webi.faculdade.dto.LocalDTO;
import br.ueg.prog.webi.faculdade.dto.TipoDTO;
import br.ueg.prog.webi.faculdade.mapper.LocalMapper;
import br.ueg.prog.webi.faculdade.mapper.TipoMapper;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.service.LocalService;
import br.ueg.prog.webi.faculdade.service.TipoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/local")
@PreAuthorize(value = "isAuthenticated()")
public class LocalController extends CrudEntityIdHashController
        <Local, LocalDTO, Long, LocalMapper, LocalService>
{

}
