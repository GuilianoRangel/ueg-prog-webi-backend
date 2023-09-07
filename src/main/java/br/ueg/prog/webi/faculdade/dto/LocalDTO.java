package br.ueg.prog.webi.faculdade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class LocalDTO {
    @Schema(description = "Identificador do objeto em Hash")
    private String idHash;

    @Schema(description = "Número identificador da sala")
    private Long numeroSala;

    @Size(max = 20)
    @Schema(description = "Nome da sala")
    private String nome;

    @Size(max = 50)
    @Schema(description = "Descricao do Nome da sala")
    private String descricao;

    @Schema(description = "Lista de chaves do local")
    private Set<ChaveDTO> chaves;
}
