package br.ueg.prog.webi.adminmodule.dto.filtros;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados do filtro de pesquisa de Grupos")
public @Data class FiltroGrupoDTO implements Serializable {
    private static final long serialVersionUID = 7616722014159043532L;

    @Schema(description =  "Nome do Grupo")
    private String nome;

    @Schema(description =  "Grupo ativo")
    private Boolean ativo;

    @Schema(description =  "Identificação do Módulo")
    private Long idModulo;

    @Schema(description =  "Id usuario logado")
    private Long idUsuario;

    /**
     * @return the id
     */
    @JsonIgnore
    public StatusAtivoInativo getIdStatusEnum() {
        StatusAtivoInativo status = null;

        if (Objects.nonNull(this.getAtivo())) {
            status = StatusAtivoInativo.getById(this.getAtivo());
        }
        return status;
    }



}
