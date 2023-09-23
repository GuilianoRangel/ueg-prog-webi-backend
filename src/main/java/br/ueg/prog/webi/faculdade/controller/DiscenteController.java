package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.DiscenteDTO;
import br.ueg.prog.webi.faculdade.mapper.DiscenteMapper;
import br.ueg.prog.webi.faculdade.model.Discente;
import br.ueg.prog.webi.faculdade.service.DiscenteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/discente")
@PreAuthorize(value = "isAuthenticated()")
public class DiscenteController extends CrudController
    <Discente, DiscenteDTO, Long, DiscenteMapper, DiscenteService>
{

}
