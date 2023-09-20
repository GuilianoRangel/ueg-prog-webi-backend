package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.AmigoDTO;
import br.ueg.prog.webi.faculdade.dto.TipoDTO;
import br.ueg.prog.webi.faculdade.mapper.AmigoMapper;
import br.ueg.prog.webi.faculdade.mapper.TipoMapper;
import br.ueg.prog.webi.faculdade.model.Amigo;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.service.AmigoService;
import br.ueg.prog.webi.faculdade.service.TipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${app.api.base}/amigo")
@PreAuthorize(value = "isAuthenticated()")
public class AmigoController extends CrudController
    <Amigo, AmigoDTO, Long, AmigoMapper, AmigoService>
{

}
