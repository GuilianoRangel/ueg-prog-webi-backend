package br.ueg.prog.webi.adminmodule.dto.filtros;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe de transferência referente aos dados filtro para Usuário.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de dados de filtro de Usuário")
public @Data class FiltroUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 3180319002111253549L;

    @Size(max = 39)
    @Schema(description = "Login do Usuário")
    private String login;

    @Size(max = 100)
    @Schema(description = "Nome do Usuário")
    private String nome;

    @Schema(description = "Usuário Ativo")
    private Boolean ativo;

    /**
     * @return the id
     */
    @JsonIgnore
    public StatusAtivoInativo getStatusEnum() {
    	StatusAtivoInativo status = null;

        if (Objects.nonNull(this.ativo)) {
            status = StatusAtivoInativo.getById(this.ativo);
        }
        return status;
    }

}
