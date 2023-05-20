package br.ueg.prog.webi.faculdade.controller;

import br.ueg.prog.webi.api.exception.MessageResponse;
import br.ueg.prog.webi.faculdade.dto.AlunoDTO;
import br.ueg.prog.webi.faculdade.dto.AlunoDadosAlteravelDTO;
import br.ueg.prog.webi.faculdade.dto.AlunoListaDTO;
import br.ueg.prog.webi.faculdade.mapper.AlunoMapper;
import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${app.api.base}/aluno")
public class AlunoController {

    private final AlunoMapper alunoMapper;

    private final AlunoService alunoService;

    public AlunoController(AlunoMapper alunoMapper, AlunoService alunoService) {
        this.alunoMapper = alunoMapper;
        this.alunoService = alunoService;
    }

    @GetMapping()
    @Operation(description = "Listagem Geral de alunos", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de alunos",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AlunoListaDTO.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<AlunoListaDTO>> listAll(){
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunoMapper.toDTO(alunos));
    }

    @PostMapping
    @Operation(description = "Método utilizado para realizar a inclusão de um aluno", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno Incluído",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoDTO.class) )),
            @ApiResponse(responseCode = "400", description = "Campos Obrigatórios não informados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            )
    })
    public ResponseEntity<AlunoDTO> incluir(@Valid @RequestBody AlunoDadosAlteravelDTO aluno){
        //prepração para entrada.
        Aluno alunoIncluir = this.alunoMapper.toModelo(aluno);

        //chamada do serviço
        System.out.println(alunoIncluir);
        alunoIncluir = this.alunoService.incluir(alunoIncluir);

        //preparação para o retorno
        AlunoDTO retorno = this.alunoMapper.toAlunoDTO(alunoIncluir);
        return ResponseEntity.ok(retorno);
    }

    @PutMapping(path = "/{id}")
    @Operation(description = "Método utilizado para altlerar os dados de um aluno", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno Alterado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoDadosAlteravelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aluno Não encontrado", content = @Content(mediaType = "application/json"))})
    public AlunoDTO alterar(@RequestBody() AlunoDadosAlteravelDTO aluno, @PathVariable(name = "id") Long matricula ){
        Aluno pAluno = alunoMapper.toModelo(aluno);
        Aluno alterar = alunoService.alterar(pAluno, matricula);
        return alunoMapper.toAlunoDTO(alterar);
    }
    @DeleteMapping(path ="/{id}")
    @Operation(description = "Método utililzado para remover um aluno pela Matricula informada", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno Removido", content = @Content(mediaType = "application/json"))})
    public AlunoDTO remover(@PathVariable(name = "id") Long id){
        Aluno alunoExcluido = this.alunoService.excluir(id);
        return alunoMapper.toAlunoDTO(alunoExcluido);
    }

    @GetMapping(path = "/{id}")
    @Operation(description = "Obter os dados completos de um aluno pela matricula(id) informado!", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno informado no ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aluno Não encontrado", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<AlunoDTO> ObterPorId(@PathVariable(name = "id") Long id){
        Aluno aluno = this.alunoService.obterPeloId(id);
        return ResponseEntity.ok(this.alunoMapper.toAlunoDTO(aluno));
    }

    @PostMapping(path = "/pesquisar")
    @Operation(description = "Busca alunos pelos dados informados", responses = {
            @ApiResponse(responseCode = "200", description = "Listagem de aluno pela pesquisa",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AlunoListaDTO.class))
                    ))})
    public List<AlunoListaDTO> pesquisar(
            @RequestBody AlunoDTO aluno
    ){
        Aluno alunoBusca = this.alunoMapper.toModelo(aluno);
        List<Aluno> localizar = alunoService.localizar(alunoBusca);
        return this.alunoMapper.toDTO(localizar);
    }

    @PatchMapping(path = "/{id}/cancelar-matricula")
    @Operation(description = "Cancela a matricula de um aluno", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno com Matricula Cancelada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Aluno Não encontrado", content = @Content(mediaType = "application/json"))
    })
    public AlunoDTO cancelar(@PathVariable(name = "id") Long id){
        Aluno aluno = this.alunoService.cancelarMatricula(id);
        return this.alunoMapper.toAlunoDTO(aluno);
    }
}
