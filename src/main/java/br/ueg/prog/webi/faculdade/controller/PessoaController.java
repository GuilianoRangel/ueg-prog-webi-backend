package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.PessoaDTO;
import br.ueg.prog.webi.faculdade.mapper.PessoaMapper;
import br.ueg.prog.webi.faculdade.model.Pessoa;
import br.ueg.prog.webi.faculdade.service.PessoaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/pessoa")
@PreAuthorize(value = "isAuthenticated()")
public class PessoaController extends CrudController
    <Pessoa, PessoaDTO, Long, PessoaMapper, PessoaService>
{

}
