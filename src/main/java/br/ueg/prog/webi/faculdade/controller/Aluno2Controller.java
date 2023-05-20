package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.AlunoDTO;
import br.ueg.prog.webi.faculdade.mapper.Aluno2Mapper;
import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.service.Aluno2Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/aluno2")
public class Aluno2Controller extends CrudController
    <Aluno, AlunoDTO, Long, Aluno2Mapper, Aluno2Service>
{
}
