package br.ueg.prog.webi.adminmodule.controller;

import br.ueg.prog.webi.adminmodule.dto.GrupoEstatisticasDTO;
import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroGrupoDTO;
import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroUsuarioDTO;
import br.ueg.prog.webi.adminmodule.dto.model.GrupoDTO;
import br.ueg.prog.webi.adminmodule.mapper.GrupoMapper;
import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.service.GrupoService;
import br.ueg.prog.webi.adminmodule.service.UsuarioGrupoService;
import br.ueg.prog.webi.api.dto.CredencialDTO;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Grupo API", description = "Manutenção de Grupos de usuários")
@RestController
@RequestMapping(path = "${app.api.base}/grupos")
public class GrupoController extends ModuleAdminAbstractController {

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private GrupoService grupoService;

    /***
     * Recupera grupos vinculados ao usuário
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Recupera os grupos pelo usuário logado.", operationId = "getGruposByUsuarioLogado", method = "getGruposByUsuarioLogadoM",
            responses = {
            @ApiResponse(responseCode = "200", description = "Listagem de grupos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GrupoDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping(path = "/user")
    public ResponseEntity<?> getGruposByUsuarioLogado() {
        CredencialDTO credential = getCredential();
        Long id = credential.getId();
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        List<Grupo> grupos = usuarioGrupoService.getUsuarioGrupos(id);
        for (Grupo grupo: grupos) {
            GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
            grupoDTO.setGrupoFuncionalidades(null);
            gruposDTO.add(grupoDTO);
        }
        return ResponseEntity.ok(gruposDTO);
    }

    @PreAuthorize("hasRole('ROLE_GRUPO_INCLUIR')")
    @PostMapping
    @Operation(description = "Incluir grupo de acesso.", responses = {
            @ApiResponse(responseCode = "200", description = "Grupo Incluído",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GrupoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<?> incluir(
            @Parameter(description = "Informações de Grupo", required = true)
                @Valid @RequestBody GrupoDTO grupoDTO
    ) {
            Grupo grupo = grupoMapper.toEntity(grupoDTO);
            return ResponseEntity.ok(grupoMapper.toDTO(grupoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link GrupoDTO} na base de dados.
     *
     * @param id
     * @param grupoTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ALTERAR')")
    @Operation(description = "Altera as informações de Grupo.", responses = {
            @ApiResponse(responseCode = "200", description = "Grupo Incluído",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GrupoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @Parameter(description = "Código do Sistema", required = true)
                @PathVariable final BigDecimal id,
            @Parameter(description = "Informações de Grupo", required = true)
                @Valid @RequestBody GrupoDTO grupoTO
    ) {
        Grupo grupo = grupoMapper.toEntity(grupoTO);
        grupo.setId(id.longValue());
        Grupo grupoSaved = grupoService.salvar(grupo);
        return ResponseEntity.ok(grupoMapper.toDTO(grupoSaved));
    }

    /**
     * Retorna a instância de {@link GrupoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @Operation(description = "Retorna as informações do Grupo pelo id informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Grupo pesquisado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GrupoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(
            @Parameter(description = "Código do Grupo", required = true)
            @PathVariable final BigDecimal id) {
        Grupo grupo = grupoService.getGrupoByIdFetch(id.longValue());
        GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);

        return ResponseEntity.ok(grupoDTO);
    }

    /**
     * Retorna a buscar de {@link Grupo} por {@link FiltroUsuarioDTO}
     *
     * @param filtroGrupoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @Operation(description = "Recupera as informações de Grupo conforme dados informados no filtro de busca", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Grupo pelo filtro",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GrupoDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(
            @Parameter(description = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroGrupoDTO filtroGrupoDTO) {
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        List<Grupo> grupos = grupoService.getGruposByFiltro(filtroGrupoDTO);
        if(grupos.size() > 0){
            for (Grupo g:
             grupos) {
                GrupoDTO grupoDTO = grupoMapper.toDTO(g);
                grupoDTO.setGrupoFuncionalidades(null);
                gruposDTO.add(grupoDTO);
            }
        }

        return ResponseEntity.ok(gruposDTO);
    }

	/**
	 * Retorna uma lista de {@link GrupoDTO} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
    @Operation(description = "Retorna uma lista de Grupos ativos conforme o 'id' do Sistema informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Grupo",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GrupoDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
	@GetMapping(path = "/grupo/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAtivos() {
		List<Grupo> grupos = grupoService.getAtivos();
		List<GrupoDTO> gruposDTO = new ArrayList<>();
		for (Grupo grupo : grupos) {
			grupo.setGrupoFuncionalidades(null);
			GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
			grupoDTO.setGrupoFuncionalidades(null);
			gruposDTO.add(grupoDTO);
		}
		return ResponseEntity.ok(gruposDTO);
	}

    /**
     * Retorna uma lista de {@link GrupoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Retorna uma lista de Grupos cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Grupo",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GrupoDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGrupos() {
        List<Grupo> grupos = grupoService.getCadastrados();
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        for (Grupo grupo : grupos) {
            grupo.setGrupoFuncionalidades(null);
            GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
            grupoDTO.setGrupoFuncionalidades(null);
            gruposDTO.add(grupoDTO);
        }
        return ResponseEntity.ok(gruposDTO);
    }

    /**
     * Inativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ATIVAR_INATIVAR')")
    @Operation(description = "Inativa o Grupo pelo id informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Grupo Inativado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @PutMapping(path = "/{id:[\\d]+}/inativo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> inativar(@Parameter(description = "Id do Grupo", required = true) @PathVariable final BigDecimal id) {
        grupoService.inativar(id.longValue());
        return ResponseEntity.ok().build();
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ATIVAR_INATIVAR')")
    @Operation(description = "Ativa o Grupo pelo id informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Grupo Inativado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @PutMapping(path = "/{id:[\\d]+}/ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> ativar(@Parameter(description = "Id do Grupo", required = true) @PathVariable final BigDecimal id) {
        grupoService.ativar(id.longValue());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Retorna Estatisticas de Usuários pro grupo.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Grupo",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GrupoEstatisticasDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping(path = "/estatisticas",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGruposEstatisticas() {
        List<GrupoEstatisticasDTO> grupoEstatisticas = grupoService.getGrupoEstatisticas();
        return ResponseEntity.ok(grupoEstatisticas);
    }

    //@PreAuthorize("isAuthenticated()")

    @Operation(description = "Retorna Relatório de Grupos.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Grupo",
                    content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping(path = "/relatorio-usuarios",produces = { MediaType.APPLICATION_PDF_VALUE })
    public ResponseEntity<?> getRelatorioGrupos () throws IOException, JRException {
        return this.toPDF(grupoService.gerarRelatorio(),"Relatorio-grupo.pdf");
    }
}
