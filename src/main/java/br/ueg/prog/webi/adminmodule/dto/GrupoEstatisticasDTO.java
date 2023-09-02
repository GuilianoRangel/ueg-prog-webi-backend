package br.ueg.prog.webi.adminmodule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Estatística de Grupo")
public @Data
class GrupoEstatisticasDTO implements Serializable {

    @Schema(description = "id do Grupo")
    private Long id;

    @Schema(description = "Nome do Grupo")
    private String nome;

    @Schema(description = "Descrição do Grupo")
    private String descricao;

    @Schema(description = "Total de Usuários do grupo")
    private Long totalUsuarios;

}
