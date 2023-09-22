package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.FuncionarioDTO;
import br.ueg.prog.webi.faculdade.mapper.FuncionarioMapper;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.service.FuncionarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/funcionario")
@PreAuthorize(value = "isAuthenticated()")
public class FuncionarioController extends CrudController
    <Funcionario, FuncionarioDTO, Long, FuncionarioMapper, FuncionarioService>
{

}
