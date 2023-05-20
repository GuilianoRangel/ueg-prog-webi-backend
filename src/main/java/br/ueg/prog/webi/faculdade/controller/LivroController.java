package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.LivroDTO;
import br.ueg.prog.webi.faculdade.mapper.LivroMapper;
import br.ueg.prog.webi.faculdade.model.Livro;
import br.ueg.prog.webi.faculdade.service.LivroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/livro")
public class LivroController extends CrudController
    <Livro, LivroDTO, Long,
            LivroMapper,
            LivroService
            >
{
}
