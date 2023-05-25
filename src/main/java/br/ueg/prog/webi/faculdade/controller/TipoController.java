package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.faculdade.dto.TipoDTO;
import br.ueg.prog.webi.faculdade.mapper.TipoMapper;
import br.ueg.prog.webi.faculdade.model.Tipo;
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
@RequestMapping(path = "${app.api.base}/tipo")
@PreAuthorize(value = "isAuthenticated()")
public class TipoController extends CrudController
    <Tipo, TipoDTO, Long, TipoMapper, TipoService>
{
    @PatchMapping(path = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Método utilizado para desativar Tipo", responses = {
            @ApiResponse(responseCode = "200", description = "Tipo desativado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TipoDTO.class) ))
    })
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<TipoDTO> desativar(@PathVariable(name = "id") long id){
        Tipo tipo = this.service.desativar(id);
        return ResponseEntity.ok(this.mapper.toDTO(tipo));
    }
    @PatchMapping(path = "/{id}/ativar",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Método utilizado para ativar Tipo", responses = {
            @ApiResponse(responseCode = "200", description = "Tipo Ativado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TipoDTO.class) ))
    })
    @PreAuthorize(value = "hasRole('SECRETARIA')")
    public ResponseEntity<TipoDTO> ativar(@PathVariable(name = "id") long id){
        Tipo tipo = this.service.desativar(id);
        return ResponseEntity.ok(this.mapper.toDTO(tipo));
    }
}
